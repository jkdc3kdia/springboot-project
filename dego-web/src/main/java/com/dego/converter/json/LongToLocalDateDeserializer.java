package com.dego.converter.json;

import com.dego.util.DateUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class LongToLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        Date targetDate = null;
        String originDate = p.getText();
        if (StringUtils.isNotEmpty(originDate)) {
            long longDate = Long.valueOf(originDate.trim());
            if(longDate==0L) {
                return null;
            }
            return DateUtils.longToLocalDate(longDate);
        }
        return null;
    }

    @Override
    public Class<?> handledType() {
        return LocalDate.class;
    }
}
