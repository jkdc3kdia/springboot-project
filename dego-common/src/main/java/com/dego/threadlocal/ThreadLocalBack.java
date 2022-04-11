package com.dego.threadlocal;


import com.dego.entity.base.RequestHeader;
import com.dego.exception.web.ResponseCode;
import com.dego.threadlocal.cache.EmployeeCache;

public class ThreadLocalBack {
    /**
     * 当前用户信息
     */
    private static final ThreadLocal<EmployeeCache> USER_INFO = new InheritableThreadLocal<>();
    public static void setCurrentUser(EmployeeCache employeeCache) {
        USER_INFO.set(employeeCache);
    }
    public static EmployeeCache getCurrentUser() {
        return USER_INFO.get();
    }
    public static void removeUserInfo() {
        USER_INFO.remove();
    }


    private static final ThreadLocal<Boolean> IS_DEBUG = new InheritableThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };
    public static void setDebug(boolean debug) {
        IS_DEBUG.set(debug);
    }
    public static boolean isDebug() {
        return IS_DEBUG.get();
    }
    public static void removeIsDebug() {
        IS_DEBUG.remove();
    }


    private static final ThreadLocal<ResponseCode> RESPONSE_CODE = new InheritableThreadLocal<>();
    public static ResponseCode getResponseCode() {
        return RESPONSE_CODE.get();
    }
    public static void setResponseCode(ResponseCode responseCode) {
        RESPONSE_CODE.set(responseCode);
    }
    public static void removeResponseCode(){
        RESPONSE_CODE.remove();
    }


    private static final ThreadLocal<RequestHeader> REQUEST_HEADER = new InheritableThreadLocal<>();
    public static void setRequestHeader(RequestHeader requestHeader) {
        REQUEST_HEADER.set(requestHeader);
    }
    public static RequestHeader getRequestHeader(){
        return REQUEST_HEADER.get();
    }
    public static void removeRequestHeader(){
        REQUEST_HEADER.remove();
    }


}
