package org.ares.cloud.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author hugo
 * @version 1.0
 * @description: BigDecimal序列化为保留两位小数的字符串
 * @date 2025/6/25 23:53
 */
public class CustomBigDecimalSerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            BigDecimal scaled = value.setScale(2, RoundingMode.HALF_UP);
            gen.writeString(scaled.toPlainString());
        } else {
            gen.writeString("0.00");
//            gen.writeNull();
        }
    }
}
