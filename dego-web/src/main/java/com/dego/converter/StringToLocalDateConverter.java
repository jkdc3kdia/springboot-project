package com.dego.converter;

import com.dego.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

/**
 * Get请求时间戳参数转LocalDate
 */
@Slf4j
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {

        if (StringUtils.isNotEmpty(source)) {
            try {
                Long timestamp = Long.valueOf(source.trim());
                return DateUtils.longToLocalDate(timestamp);
            } catch (NumberFormatException e) {
                log.warn("时间参数格式错误：非长整型时间戳");
            }
        }
        return null;

    }
}
