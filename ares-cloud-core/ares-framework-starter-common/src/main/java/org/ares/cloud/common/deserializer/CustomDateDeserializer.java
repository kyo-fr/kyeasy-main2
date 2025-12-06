package org.ares.cloud.common.deserializer;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.utils.DateUtils;

import java.io.IOException;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/1/6 22:29
 */
public class CustomDateDeserializer extends JsonDeserializer<Long> {
    @Override
    public Long deserialize(com.fasterxml.jackson.core.JsonParser p, DeserializationContext ctxt) throws IOException {
        long longValue = p.getLongValue();
        if (longValue > 0) {
            String zoneId = ApplicationContext.getZoneId();
            if (zoneId == null) {
                zoneId = "Asia/Shanghai";
            }
            return DateUtils.convertTimezoneToUtc(longValue, zoneId);
        }
        return longValue;
    }
}