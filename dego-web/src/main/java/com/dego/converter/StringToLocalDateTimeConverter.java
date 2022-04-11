package com.dego.converter;

import com.dego.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

/**
 * Get请求时间戳参数转LocalDateTime
 */
@Slf4j
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String source) {
        if (StringUtils.isNotEmpty(source) ) {
            try {
                Long timestamp = Long.valueOf(source.trim());
                return DateUtils.longToLocalDateTime(timestamp);
            } catch (NumberFormatException e) {
                log.warn("时间参数格式错误：非长整型时间戳");
            }
        }
        return null;
    }
}
