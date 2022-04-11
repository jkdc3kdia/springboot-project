package com.dego.util.lock;

import com.dego.exception.BusinessException;
import com.dego.exception.web.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 基于redisson分布式锁进行封装
 * 提供了一些常用方法，如有需要可自行扩展
 *
 */
@Slf4j
@Component
public class RedissonDistributedLock {

    @Autowired
    private RedissonClient redisson;

    /**
     * 无返回值锁任务；
     * 时间单位：毫秒
     *
     * @param task     无返回值任务
     * @param lockKey  锁定key
     * @param waitTime 最大等待时间
     * @param lockTime 最大锁定时间
     */
    public void execute(NoResultTask task, String lockKey, long waitTime, long lockTime) {
        execute(task, lockKey, waitTime, lockTime, TimeUnit.MILLISECONDS);
    }

    public void execute(NoResultTask task, String lockKey, long waitTime, long lockTime, TimeUnit unit) {
        execute(() -> {
            task.run();
            return null;
        }, lockKey, waitTime, lockTime, unit);
    }

    /**
     * 任务锁；
     * 时间单位：毫秒
     *
     * @param task     任务
     * @param lockKey  锁的key
     * @param waitTime 最大等待时间
     * @param lockTime 最大锁定时间
     * @param <R>      返回值类型
     * @return
     */
    public <R> R execute(Task<R> task, String lockKey, long waitTime, long lockTime) {
        return execute(task, lockKey, waitTime, lockTime, TimeUnit.MILLISECONDS);
    }

    public <R> R execute(Task<R> task, String lockKey, long waitTime, long lockTime, TimeUnit unit) {
        RLock rLock = redisson.getLock(lockKey);
        return execute(rLock, task, waitTime, lockTime, unit);
    }

    /**
     * 无返回值【联锁】任务
     * 时间单位：毫秒
     *
     * @param task     无返回值任务
     * @param lockKeys 锁的key列表
     * @param waitTime 最大等待时间
     * @param lockTime 最大锁定时间
     */
    public void execute(NoResultTask task, List<String> lockKeys, long waitTime, long lockTime) {
        RLock rLock = redisson.getMultiLock(lockKeys.stream().map(redisson::getLock).toArray(RLock[]::new));
        execute(rLock, () -> {
            task.run();
            return null;
        }, waitTime, lockTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 联锁（多锁）
     *
     * @param task     任务
     * @param lockKeys 联锁的key列表
     * @param waitTime 最大等待时间
     * @param lockTime 最大锁定时间
     * @param unit     时间单位
     * @param <R>
     * @return
     */
    public <R> R execute(Task<R> task, List<String> lockKeys, long waitTime, long lockTime, TimeUnit unit) {
        RLock rLock = redisson.getMultiLock(lockKeys.stream().map(redisson::getLock).toArray(RLock[]::new));
        return execute(rLock, task, waitTime, lockTime, unit);
    }

    /**
     * 加锁核心实现
     *
     * @param rLock    锁由外部传入，普通锁/联锁
     * @param task     任务
     * @param waitTime 最大等待时间
     * @param lockTime 最大锁定时间
     * @param unit     时间单位
     * @param <R>      返回值类型
     * @return 任务返回值
     */
    public <R> R execute(RLock rLock, Task<R> task, long waitTime, long lockTime, TimeUnit unit) {
        boolean locked = false;
        try {
            if (locked = rLock.tryLock(waitTime, lockTime, unit)) {
                log.info("【redisson锁】：获取到锁，开始执行任务！");
                return task.run();
            }

            log.error("【redisson锁】：", ResponseCode.LOCKED_TIMEOUT.getMsg());
            throw new BusinessException(ResponseCode.LOCKED_TIMEOUT);
        } catch (InterruptedException ie) {
            log.error("【redisson锁】：", ResponseCode.LOCKED_FAIL.getMsg());
            throw new BusinessException(ResponseCode.LOCKED_FAIL);
        } finally {
            if (locked) {
                log.info("【redisson锁】：释放锁！");
                rLock.unlock();
            }
        }
    }
}
