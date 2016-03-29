package com.enernoc.bdap.sonic.datamodel.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Clock;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * Serializes a ZonedDateTime to the ISO_INSTANT format "yyyy-MM-dd'T'HH:mm:ssZ"
 *
 * @author miguel.reyes on 3/28/16.
 */
public class ZonedDateTimeConverter implements JsonSerializer<ZonedDateTime>, JsonDeserializer<ZonedDateTime> {

    @Override
    public JsonElement serialize(ZonedDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        String formattedZonedDateTime = src.format(DateTimeFormatter.ISO_INSTANT);
        return new JsonPrimitive(formattedZonedDateTime);
    }

    @Override
    public ZonedDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String asString = json.getAsString();
        return ZonedDateTime.ofInstant(Instant.parse(asString), Clock.systemUTC().getZone());
    }
}
