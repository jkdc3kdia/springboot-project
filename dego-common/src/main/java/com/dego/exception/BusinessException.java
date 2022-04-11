package com.dego.exception;


import com.dego.exception.web.ResponseCode;

/**
 * 业务逻辑异常
 */
public class BusinessException extends RuntimeException {

    private ResponseCode responseCode;

    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getMsg());
        this.responseCode = responseCode;
    }

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(Exception e, String msg) {
        super(e instanceof BusinessException ? e.getMessage():msg);
        if(e instanceof BusinessException) {

        }
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
