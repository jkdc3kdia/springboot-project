package com.dego.shiro;

import com.dego.constant.SystemConstant;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ShiroUserFilter extends UserFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean accessAllowed = super.isAccessAllowed(request, response, mappedValue);
        if(!accessAllowed){
            // 直接 response.getWriter().write() 写出浏览器会有跨域问题 这里做标记在拦截器中返回
            request.setAttribute(SystemConstant.TO_LOGIN,true);
        }
        return true;
    }
}
