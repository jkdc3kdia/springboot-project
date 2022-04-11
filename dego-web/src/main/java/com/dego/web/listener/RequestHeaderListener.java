package com.dego.web.listener;

import com.dego.entity.base.RequestHeader;
import com.dego.threadlocal.ThreadLocalBack;
import com.dego.util.BeanUtils;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 得到请求头的信息
 */
@WebListener
public class RequestHeaderListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ThreadLocalBack.removeRequestHeader();
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();

        Map<String, Object> headerMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            headerMap.put(key, value);
        }
        RequestHeader header = BeanUtils.copyJsonParse(headerMap, RequestHeader.class);
        ThreadLocalBack.setRequestHeader(header);
    }
}
