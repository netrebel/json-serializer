package com.enernoc.bdap.sonic.common.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.ZonedDateTime;

/**
 * @author miguel.reyes on 3/7/16.
 */
public class Json {

    private Json() {
    }

    public static Gson createGson() {
        GsonBuilder builder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        builder.registerTypeAdapter(Class.class, new ClassTypeConverter());
        builder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeConverter());
        return builder.create();
    }

}
