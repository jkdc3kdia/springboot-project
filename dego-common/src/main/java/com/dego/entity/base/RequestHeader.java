package com.dego.entity.base;

import lombok.Data;

@Data
public class RequestHeader {

    /**
     * 加密验证
     */
    private String sign;

    /**
     * 请求时间
     */
    private String time;

    /**
     * 随机字符串
     */
    private String randomStr;

    /**
     * 小程序用户token
     */
    private String token;

    /**
     * 终端设备类型
     */
    private String deviceType;

    /**
     * 来源
     */
    private String referer;
}
