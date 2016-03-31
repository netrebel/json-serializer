package com.miguel.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializes a ZonedDateTime to the ISO_INSTANT format "yyyy-MM-dd'T'HH:mm:ssZ"
 *
 * @author miguel.reyes on 3/31/16.
 */
public class CustomZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

    @Override
    public void serialize(ZonedDateTime src, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(src.toInstant(), Clock.systemUTC().getZone());
        gen.writeString(zonedDateTime.format(DateTimeFormatter.ISO_INSTANT));
    }
}
