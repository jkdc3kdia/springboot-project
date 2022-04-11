package com.dego.web.aspect;

import com.alibaba.fastjson.JSON;
import com.dego.threadlocal.ThreadLocalBack;
import com.dego.threadlocal.cache.EmployeeCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 记录操作日志
 */
@Slf4j
@Aspect
@Component
public class OperateRecordAspect {


    @Around("execution(public * com.dego.controller.*.*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        List<Object> params = new ArrayList<>();
        for (Object arg : args) {
            if (!(arg instanceof HttpServletResponse) && !(arg instanceof HttpServletRequest)) {
                params.add(arg);
            }
        }
        EmployeeCache currentUser = ThreadLocalBack.getCurrentUser();
        if (currentUser == null) currentUser = new EmployeeCache();
        log.info("请求地址 : {} , 参数 : {}，请求用户信息:{}，{}", request.getRequestURI(),
                JSON.toJSONString(params), currentUser.getRealName(), currentUser.getAccount());

        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        log.info("方法>>>>>>>{}", method.getName());
        return joinPoint.proceed();
    }
}
