package com.enernoc.bdap.sonic.common.gson;

import com.google.gson.Gson;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertEquals;

/**
 * @author miguel.reyes on 3/28/16.
 */
public class JsonTest {

    /**
     * Match regex pattern: ^\{.*\.\d\d\dZ\".*\}$
     */
    private final Matcher<String> matcher = matchesPattern("^\\{.*\\.\\d\\d\\dZ\".*\\}$");
    private Gson gson = Json.createGson();
    private SimplePojo pojo;

    @Before
    public void before() {
        pojo = createSamplePojo();
    }

    @Test
    public void testSerializationUTCTime() throws Exception {
        pojo.zonedDateTime = ZonedDateTime.now(Clock.systemUTC());
        String json = gson.toJson(pojo);
        System.out.println("pojo = " + json);

        assertThat(json, matcher);
    }

    @Test
    public void testSerializationWestCoastTime() throws Exception {
        pojo.zonedDateTime = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
        String json = gson.toJson(pojo);
        System.out.println("pojo = " + json);

        assertThat(json, matcher);
    }

    @Test
    public void testDeserialization() throws Exception {
        String json = "{\"paramString\":\"hello\",\"paramLong\":1234,\"paramBoolean\":true,\"zonedDateTime\":\"2016-03-28T21:10:12.326Z\",\"simpleDate\":\"2016-03-28T17:10:12-0400\"}";
        SimplePojo pojoFromJson = gson.fromJson(json, SimplePojo.class);
        assertEquals(28, pojoFromJson.zonedDateTime.getDayOfMonth());
        assertEquals(3, pojoFromJson.zonedDateTime.getMonthValue());
        assertEquals(2016, pojoFromJson.zonedDateTime.getYear());
        assertEquals("Z", pojoFromJson.zonedDateTime.getZone().getId());
    }

    private SimplePojo createSamplePojo() {
        SimplePojo pojo = new SimplePojo();
        pojo.paramString = "hello";
        pojo.paramBoolean = true;
        pojo.paramLong = 1234L;
        pojo.zonedDateTime = ZonedDateTime.now(Clock.systemUTC());
        pojo.simpleDate = Date.from(Instant.now(Clock.systemUTC()));
        return pojo;
    }

    public static class SimplePojo {
        public String paramString;
        public Long paramLong;
        public boolean paramBoolean;
        public ZonedDateTime zonedDateTime;
        public Date simpleDate;
    }


}