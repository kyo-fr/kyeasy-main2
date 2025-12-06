package org.ares.cloud.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ares.cloud.common.model.Money;

import java.io.IOException;

/**
 * @author hugo
 * @version 1.0
 * @description:  金额序列化
 * @date 2025/6/26 21:40
 */
public class CustomMoneySerializer   extends JsonSerializer<Money> {
    @Override
    public void serialize(Money value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(value.toStringMoney());
        } else {
            gen.writeString("0.00");
        }
    }
}
