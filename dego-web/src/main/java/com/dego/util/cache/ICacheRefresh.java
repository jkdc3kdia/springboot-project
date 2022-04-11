package com.dego.util.cache;

/**
 * 缓存刷新
 * <p>
 * 注：不要注入该接口，因为可能有多个实现
 *
 * @author huang.guo
 * @since 2019-11-11
 */
public interface ICacheRefresh {

    /**
     * 自定义缓存刷新逻辑；
     * 比如有的维护缓存，有的直接清空缓存
     *
     * @return
     */
    boolean refreshCache();
}
