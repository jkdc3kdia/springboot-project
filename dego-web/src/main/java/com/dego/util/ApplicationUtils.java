package com.dego.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 描述：静态获取SpringBean工具类
 *
 */
@Component
public class ApplicationUtils {

    private static ApplicationContext context;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static Object getBean(String name){
       return context.getBean(name);
    }

    public static <T> T  getBean(Class<T> clazz){
        return context.getBean(clazz);
    }
}
