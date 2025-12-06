package org.ares.cloud.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.utils.DateUtils;

import java.io.IOException;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/1/6 22:28
 */
public class CustomDateSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        if (value != null) {
            long formattedDate = DateUtils.convertUtcToTimezone(value, ApplicationContext.getZoneId());
            gen.writeNumber(formattedDate);
        } else {
            gen.writeNull();
        }
    }
}




