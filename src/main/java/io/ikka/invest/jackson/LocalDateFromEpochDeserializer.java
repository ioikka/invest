package io.ikka.invest.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class LocalDateFromEpochDeserializer extends StdDeserializer<LocalDate> {
    private static final long serialVersionUID = 1L;

    protected LocalDateFromEpochDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return Instant.ofEpochSecond(jp.readValueAs(Long.class)).atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
