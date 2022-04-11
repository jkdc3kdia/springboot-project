package com.dego.web;

import com.dego.exception.BusinessException;
import com.dego.exception.web.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * RestControllerAdvice
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    /**
     * 业务异常处理
     *
     * @param ex 业务异常
     * @return ApiRespResult
     */
    @ExceptionHandler(value = {BusinessException.class})
    public ApiRespResult<Object> serviceException(BusinessException ex) {
        log.error(ex.getMessage(), ex);
        ResponseCode code = ex.getResponseCode();
        if (code == null) {
            return new ApiRespResult<>(ResponseCode.ERR_MSG_SYSTEM_ERROR.getCode(), ex.getMessage());
        }

        return new ApiRespResult<>(code);
    }
    /**
     * 参数请求异常
     *
     * @param ex 参数请求异常
     * @return ApiRespResult
     */
//    @ExceptionHandler(value = {RequestParamException.class})
//    public ApiRespResult<Object> requestParameterException(RequestParamException ex) {
//        log.error(ex.getMessage(), ex);
//        return new ApiRespResult<>(ResponseCode.PARAM_INVALID.getCode(), ex.getMessage());
//    }

    /**
     * 密码错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {AuthenticationException.class})
    public ApiRespResult<Object> serviceException(AuthenticationException ex) {
        if(ex.getCause() instanceof BusinessException){
            BusinessException cause = (BusinessException) ex.getCause();
            log.error(cause.getMessage(), cause);
            return new ApiRespResult<>(ResponseCode.ERR_MSG_SYSTEM_ERROR.getCode(), cause.getMessage());
        }else{
            log.error(ex.getMessage(), ex);
            return new ApiRespResult<>(ResponseCode.ERR_MSG_SYSTEM_ERROR.getCode(), "账户或密码错误，请重新输入");
        }
    }

    /**
     * 用户不存在
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {UnknownAccountException.class})
    public ApiRespResult<Object> serviceException(UnknownAccountException ex) {
        log.error(ex.getMessage(), ex);
        return new ApiRespResult<>(ResponseCode.ERR_MSG_SYSTEM_ERROR.getCode(), "账户或密码错误，请重新输入");
    }

    /**
     * 用户权限不足
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {UnauthorizedException.class})
    public ApiRespResult<Object> serviceException(UnauthorizedException ex) {
        log.error(ex.getMessage(), ex);
        return new ApiRespResult<>(ResponseCode.ERR_MSG_PERMISSION_ERROR.getCode(), ex.getMessage());
    }

    /**
     * 数据检验异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ApiRespResult<Object> serviceException(IllegalArgumentException ex) {
        log.error(ex.getMessage(), ex);
        return new ApiRespResult<>(ResponseCode.ERR_MSG_SYSTEM_ERROR.getCode(), ex.getMessage());
    }

    /**
     * 请求参数校验异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ApiRespResult<Object> serviceException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null;
        return new ApiRespResult<>(ResponseCode.PARAM_INVALID.getCode(), fieldError.getDefaultMessage());
    }


    @ExceptionHandler(value = {Exception.class})
    public ApiRespResult<Object> serviceException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return systemErrorEx();
    }

    private ApiRespResult<Object> systemErrorEx() {
        return new ApiRespResult<>(ResponseCode.ERR_MSG_SYSTEM_ERROR.getCode(), ResponseCode.ERR_MSG_SYSTEM_ERROR.getMsg());
    }
}
