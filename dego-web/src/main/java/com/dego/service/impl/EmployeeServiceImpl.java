package com.dego.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dego.constants.SystemConstant;
import com.dego.entity.po.Employee;
import com.dego.mapper.EmployeeMapper;
import com.dego.service.IEmployeeService;
import com.dego.threadlocal.cache.EmployeeCache;
import com.dego.util.JdkSerializeUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author janus
 * @since 2021-07-12
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    protected RedisTemplate redisTemplate;


    @Override
    public void deleteOtherSession(Subject currentSubject) {
        EmployeeCache currentEmployee = (EmployeeCache) currentSubject.getPrincipal();
        Map<String, Object> maps = redisTemplate.opsForHash().entries(SystemConstant.REDIS_SESSION_KEY);
        for (String key : maps.keySet()) {
            Object o = maps.get(key);
//            SimpleSession session = (SimpleSession) JdkSerializeUtils.deserialize((byte[]) o);
            SimpleSession session = (SimpleSession) o;
            Subject everySubject = new Subject.Builder().session(session).buildSubject();
            EmployeeCache principal = (EmployeeCache) everySubject.getPrincipal();
            // 用户account相同且登陆设备类型相同才删除,保证不同的设备类型可以同时登陆相同账号
            if (principal != null && principal.getId().equals(currentEmployee.getId())
                    && !currentSubject.getSession().getId().equals(everySubject.getSession().getId())) {
                redisTemplate.opsForHash().delete(SystemConstant.REDIS_SESSION_KEY, key);
            }

        }

        /*Collection<Session> activeSessions = ((DefaultWebSessionManager) sessionManager).getSessionDAO().getActiveSessions();
        for (Session activeSession : activeSessions) {
            Subject subject = new Subject.Builder().session(activeSession).buildSubject();
            EmployeeCache employee = (EmployeeCache) subject.getPrincipal();
            if (employee != null) {
                if (!subject.getSession().getId().equals(currentSubject.getSession().getId())
                        && subject.getSession().getAttribute(SystemConstant.LOGIN_DEVICE_TYPE).equals(currentSubject.getSession().getAttribute(SystemConstant.LOGIN_DEVICE_TYPE))
                        && employee.getAccount().equals(currentEmployee.getAccount())) {
                    subject.logout();
//                    redisTemplate.opsForHash().delete(SystemConstant.REDIS_SESSION_KEY, key);
                }
            }
        }*/
    }
}
