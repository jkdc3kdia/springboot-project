package com.dego.util.cache;

import org.apache.commons.lang3.StringUtils;

/**
 * 具体业务缓存code抽象，定义了一些通用的缓存key和lockKey
 * 以及通用的code：{@link CommonCacheCode}
 * 有需要请自行在子类中扩展，由各子系统定义枚举继承提供具体的code
 * <p>
 * 缓存key示例：group:business1-business2_id
 * 锁key示例：group:L-business1-business2_id
 * <p>
 *
 * @author huang.guo
 * @since 2019-10-14
 */
public interface ICacheCode extends IEnum<String, String> {

    /**
     * 分隔符：用于group与businessCode之间拼接
     */
    String GROUP_SPLITTER = ":";

    /**
     * 分隔符2：用于多个businessCode之间拼接
     */
    String BUSINESS_SPLITTER = "-";

    /**
     * 分隔符3：用于businessCode和id之间拼接，如果多个id也用此分隔符
     */
    String ID_SPLITTER = "_";

    /**
     * 分布式锁标识
     * 分隔符4：用于group和businessCode之间
     */
    String LOCK_SPLITTER = "L";

    /**
     * 由子类提供具体group
     *
     * @return
     */
    CacheGroup group();

    /**
     * 缓存key
     *
     * @return
     */
    default String cacheKey() {
        return join(group(), getCode(), false);
    }

    /**
     * 根据id获取缓存key
     *
     * @param ids
     * @return
     */
    default String cacheKey(String... ids) {
        return join(group(), getCode(), false, ids);
    }

    /**
     * 获取lockKey
     *
     * @return
     */
    default String lockKey() {
        return join(group(), getCode(), true);
    }

    /**
     * 根据id获取lockKey
     *
     * @param ids
     * @return
     */
    default String lockKey(String... ids) {
        return join(group(), getCode(), true, ids);
    }

    /**
     * 通用缓存code拼接方法
     *
     * @param group
     * @param cacheCode
     * @param lock      是否创建的锁key，传一个参数或者不传
     * @return
     */
    default String join(CacheGroup group, String cacheCode, boolean lock) {
        return lock ? group.getCode() + GROUP_SPLITTER + LOCK_SPLITTER + BUSINESS_SPLITTER + cacheCode
                : group.getCode() + GROUP_SPLITTER + cacheCode;
    }

    /**
     * 通过缓存code
     *
     * @param group
     * @param cacheCode
     * @param ids       会转为字符串
     * @param lock      是否创建的锁key，传一个参数或者不传
     * @return
     */
    default String join(CacheGroup group, String cacheCode, boolean lock, Object... ids) {
        String joinIds = StringUtils.join(ids, ID_SPLITTER);
        return lock ? join(group, cacheCode, true) + ID_SPLITTER + joinIds
                : join(group, cacheCode, false) + ID_SPLITTER + joinIds;
    }
}
