package com.dego.config;

import com.dego.converter.StringToLocalDateConverter;
import com.dego.converter.StringToLocalDateTimeConverter;
import com.dego.converter.StringTrimConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

@Configuration
public class SpringDataConvert {
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    /**
     * 增加字符串转换为List集合
     */
    @PostConstruct
    public void addConversionConfig() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) requestMappingHandlerAdapter.getWebBindingInitializer();
        if (initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService)initializer.getConversionService();
            //添加时间戳转换为日期类型的转换器
            genericConversionService.addConverter(new StringToLocalDateTimeConverter());
            genericConversionService.addConverter(new StringToLocalDateConverter());
            genericConversionService.addConverter(new StringTrimConverter());
        }
    }
}
