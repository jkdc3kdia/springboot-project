/**
 * 定义：缓存key、锁key获取
 * <p>
 * 使用：
 * ① 在{@link com.yinli.common.util.cache.CacheGroup}中定义对应的group
 * ② 在各系统的util.cache包下定义CacheCode继承{@link com.yinli.common.util.cache.ICacheCode}
 * ③ 定义code、desc字段以及需要的枚举
 * ④ 使用：缓存key：cacheKey()、lockKey：lockKey()
 * <p>
 * 注：
 * ① 缓存key尽量简洁，不要过于复杂；
 * ② 都使用枚举形式来定义常量，不要使用"魔法值"
 *
 * @author huang.guo
 * @see com.yinli.common.util.cache.CacheGroup
 * @see com.yinli.common.util.cache.ICacheCode
 * @since 2019-10-14
 */
package com.dego.util.cache;