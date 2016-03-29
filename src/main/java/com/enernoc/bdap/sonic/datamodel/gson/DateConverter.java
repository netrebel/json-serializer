package com.enernoc.bdap.sonic.datamodel.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Clock;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Serializes a Date to the ISO_INSTANT format "yyyy-MM-dd'T'HH:mm:ssZ"
 *
 * @author miguel.reyes on 3/28/16.
 */
public class DateConverter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(src.toInstant(), Clock.systemUTC().getZone());
        String formattedDate = zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
        return new JsonPrimitive(formattedDate);
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String asString = json.getAsString();
        return Date.from(Instant.parse(asString));
    }
}
