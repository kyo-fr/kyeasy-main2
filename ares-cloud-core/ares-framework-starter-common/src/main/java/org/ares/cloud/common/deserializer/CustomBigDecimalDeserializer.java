package org.ares.cloud.common.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.ares.cloud.common.exception.RequestBadException;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author hugo
 * @version 1.0
 * @description: BigDecimal反序列化，支持数字和字符串
 * @date 2025/6/25 23:53
 */
public class CustomBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {
    @Override
    public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        switch (p.getCurrentToken()) {
            case VALUE_NUMBER_INT:
            case VALUE_NUMBER_FLOAT:
                return p.getDecimalValue();
            case VALUE_STRING:
                String text = p.getText().trim();
                if (text.isEmpty()) {
                    return null;
                }
                return new BigDecimal(text);
            default:
                throw new RequestBadException("BigDecimal反序列化失败");
        }
    }
}
