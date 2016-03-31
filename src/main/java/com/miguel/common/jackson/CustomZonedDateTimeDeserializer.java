package com.miguel.common.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * Deserialize a string in ISO_INSTANT format "yyyy-MM-dd'T'HH:mm:ssZ" to ZonedDateTime
 *
 * @author miguel.reyes on 3/31/16.
 */
public class CustomZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    @Override
    public ZonedDateTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String asString = jp.getValueAsString();
        return ZonedDateTime.ofInstant(Instant.parse(asString), Clock.systemUTC().getZone());
    }


}