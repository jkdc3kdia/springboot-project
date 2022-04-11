package com.dego.util.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 缓存系统前缀
 *
 * @author huang.guo
 * @since 2019-10-14
 */
@Getter
@AllArgsConstructor
public enum CacheGroup {
    /**
     * 公用，可能跨多系统
     */
    COMMON("com"),
    WMS("wms"),
    TS("ts"),
    BASIC("basic"),
    SPCMS("spcms"),
    UIAS("uias"),
    TMS("tms"),
    BOS("bos"),
    CMS("cms"),
    SCS("scs"),
    OMS("oms"),
    UC("uc")
    ;

    private String code;


}
