package com.dego.converter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * String去除前后空格
 */
@Slf4j
public class StringTrimConverter implements Converter<String, String> {

    @Override
    public String convert(String source) {

        if (StringUtils.isNotEmpty(source)) {
            return source.trim();
        }
        return null;

    }
}
