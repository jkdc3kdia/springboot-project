package com.dego.converter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Date;

public class LongToDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        Date targetDate = null;
        String originDate = p.getText();
        if (StringUtils.isNotEmpty(originDate)) {
            long longDate = Long.valueOf(originDate.trim());
            if(longDate==0L) {
                return null;
            }
            return new Date(longDate);
        }
        return null;
    }

    @Override
    public Class<?> handledType() {
        return Date.class;
    }
}
