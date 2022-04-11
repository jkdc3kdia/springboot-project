package com.dego.exception.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 错误码枚举
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    // 通用返回码
    MSG_OK(0, "请求成功"),
    SUCCESS(200, "成功"),
    ERR_MSG_INVALID(400, "错误的请求"),
    ERR_MSG_PERMISSION_ERROR(401, "未授权"),
    FORBIDDEN(403, "用户未登录或登录已过期"),
    ERR_MSG_RESOURCE_NOT_FOUND(404, "未找到相关的资源"),
    ERR_MSG_SYSTEM_ERROR(500, "系统内部错误"),
    INIT_PWD(521, "密码需要初始化设置"),
    PWD_NOT_SAFE(522, "密码不符合规范"),
    PARAM_INVALID(523, "参数错误"),
    INSERT_FAIL(524, "插入失败"),
    UPDATE_FAIL(525, "更新失败"),
    DATA_NOT_FIND(526, "数据未找到"),
    DATA_ILLEGAL(527, "数据不合法"),
    DATA_EXISTED(528, "数据已存在"),
    REPEAT_PAYMENT(529, "重复支付"),
    STOCK_NOT_ENOUGH(530, "库存不足"),
    STOCK_LOCK_ERROR(531, "库存锁定失败"),
    REPEAT_OPERATION(532, "重复操作"),
    USER_NOT_EXISTS(533, "用户不存在"),
    LOCKED_FAIL(534, "锁获取失败"),
    LOCKED_TIMEOUT(535, "锁获取超时"),
    ERROR_PASSWD(536, "密码错误"),
    NO_VALIDATE_CODE(537, "没有验证码"),
    VALIDATE_CODE_FAIL(538, "验证码错误"),
    AMOUNT_NOT_ENOUGH(539, "金额不足"),
    PASSWD_ERROR_MAX_COUNT(540, "密码错误次数上限"),
    REDIS_OPERATION_FAIL(541, "redis操作失败");



    private int code;
    private String msg;

    /**
     * 根据code聚合；还可以避免code重复
     */
    private final static Map<Integer, ResponseCode> CODE_MAP
            = Arrays.stream(ResponseCode.values()).collect(Collectors.toMap(ResponseCode::getCode, t -> t));


}
