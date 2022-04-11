package com.dego.web.interceptor;

import com.dego.constants.SystemConstant;
import com.dego.exception.BusinessException;
import com.dego.exception.web.ResponseCode;
import com.dego.threadlocal.ThreadLocalBack;
import com.dego.threadlocal.cache.EmployeeCache;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Boolean toLogin = (Boolean)request.getAttribute(SystemConstant.TO_LOGIN);
        if(toLogin != null && toLogin){
            throw new BusinessException(ResponseCode.FORBIDDEN);
        }
        EmployeeCache employee = (EmployeeCache) SecurityUtils.getSubject().getPrincipal();
        ThreadLocalBack.setCurrentUser(employee);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalBack.removeUserInfo();
        ThreadLocalBack.removeResponseCode();
    }
}
