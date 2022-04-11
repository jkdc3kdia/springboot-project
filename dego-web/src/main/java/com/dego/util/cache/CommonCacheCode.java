package com.dego.util.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 跨系统通用的缓存code
 *
 * @author huang.guo
 * @since 2019-10-14
 */
@AllArgsConstructor
@Getter
public enum CommonCacheCode implements ICacheCode {

    //
    SWITCH("switch","项目一些功能开关配置"),
    SWAGGER_API("swagger_api","swaggerApi"),
    SWAGGER_API_TIME("swagger_api_time","swaggerApi最后更新时间"),
    STOP_SEND_ORDER_SMS("switch-stopSendOrderSms","是否停止发送订单短信"),
    OPEN_WECHAT_OPENID_LOGIN("switch-openIdLogin","是否开启openId登陆b2c商城"),
    COMMODITY_STOCK_TS("commodity_stock_ts","站点库存缓存key"),
    COMMODITY_STOCK_WMS("commodity_stock_wms","总仓库存缓存key"),
    TS_USER_SITE("ts_user_site","ts管理员切换后的站点"),
    ORDER_PAY_SERIAL("order_pay_serial","订单支付序号"),
    ZZK_PROCESS("device_process","在处理中的周转筐")
    ;

    private String code;
    private String desc;

    @Override
    public CacheGroup group() {
        return CacheGroup.COMMON;
    }}
