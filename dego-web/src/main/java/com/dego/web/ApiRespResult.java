package com.dego.web;

import com.dego.exception.web.ResponseCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通用返回对象
 *
 * @param <T> 数据对象类型
 */
@ApiModel
@JsonIgnoreProperties
public class ApiRespResult<T> {

    @JsonIgnore
    private static final String SUCCESS_MSG = "请求成功";

    @ApiModelProperty("响应码")
    @JsonProperty("code")
    private int code = ResponseCode.MSG_OK.getCode();

    @ApiModelProperty("错误信息")
    @JsonProperty("msg")
    private String msg = SUCCESS_MSG;

    @ApiModelProperty("响应数据")
    @JsonProperty("data")
    private T data;


    @ApiModelProperty("当前时间")
    private Long currentTime;

    @ApiModelProperty(hidden = true)
    private ResponseCode responseCode;

    public ApiRespResult() {
    }

    public static ApiRespResult defaultResult() {
        return new ApiRespResult();
    }

    public ApiRespResult(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.code = responseCode.getCode();
        this.msg = responseCode.getMsg();
    }

    public ApiRespResult(T data) {
        this.data = data;
    }

    public ApiRespResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiRespResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public ResponseCode getResponseCode() {
        return this.responseCode;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }
}
