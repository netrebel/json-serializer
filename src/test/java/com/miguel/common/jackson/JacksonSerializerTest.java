package com.miguel.common.jackson;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertEquals;

/**
 * @author miguel.reyes on 3/31/16.
 */
public class JacksonSerializerTest {

    /**
     * Match regex pattern: ^\{.*\.\d\d\dZ\".*\}$
     */

    private final Matcher<String> matcher = matchesPattern("^\\{.*zoned_date_time\\\":\\d*.*\\}$");
    private ObjectMapper objectMapper = JacksonSerializer.createMapper();
    private SimplePojo pojo;

    @Before
    public void before() {
        pojo = createSamplePojo();
    }

    @Test
    public void testSerializationUTCTime() throws Exception {
        pojo.zonedDateTime = ZonedDateTime.now(Clock.systemUTC());
        String json = objectMapper.writeValueAsString(pojo);
        System.out.println("pojo = " + json);

        assertThat(json, matcher);
    }

    @Test
    public void testSerializationWestCoastTime() throws Exception {
        pojo.zonedDateTime = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
        String json = objectMapper.writeValueAsString(pojo);
        System.out.println("pojo = " + json);

        assertThat(json, matcher);
    }

    @Test
    public void testDeserialization() throws Exception {
        String json = "{\"param_string\":\"hello\",\"param_long\":1234,\"param_boolean\":true,\"zoned_date_time\": 1459443301466,\"simple_date\":\"2016-03-28T21:10:12.326Z\"}";
        System.out.println("Deserialize json = " + json);
        SimplePojo pojoFromJson = objectMapper.readValue(json, SimplePojo.class);
        assertEquals(31, pojoFromJson.zonedDateTime.getDayOfMonth());
        assertEquals(3, pojoFromJson.zonedDateTime.getMonthValue());
        assertEquals(2016, pojoFromJson.zonedDateTime.getYear());
        assertEquals("UTC", pojoFromJson.zonedDateTime.getZone().getId());
    }

    @Test
    public void testListDeserialization() throws Exception {
        SimplePojo sp1 = createSamplePojo();
        SimplePojo sp2 = createSamplePojo();
        List<SimplePojo> list = new ArrayList<>();
        list.add(sp1);
        list.add(sp2);

        //First make a json from a List
        String json = objectMapper.writeValueAsString(list);
        String expectedJson = "[{\"param_string\":\"hello\",\"param_long\":1234,\"param_boolean\":true,\"zoned_date_time\":1463562000000,\"simple_date\":808234200000},{\"param_string\":\"hello\",\"param_long\":1234,\"param_boolean\":true,\"zoned_date_time\":1463562000000,\"simple_date\":808234200000}]";
        assertEquals(expectedJson, json);

        //Verify that the Json String can be deserialized into a list of objects.
        JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, SimplePojo.class);
        List<SimplePojo> parsedList = objectMapper.readValue(expectedJson, type);
        assertEquals(2, parsedList.size());

        System.out.println("json = " + json);
    }

    private SimplePojo createSamplePojo() {
        SimplePojo pojo = new SimplePojo();
        pojo.paramString = "hello";
        pojo.paramBoolean = true;
        pojo.paramLong = 1234L;

//        pojo.localDateTime = LocalDateTime.now(ZoneId.of("America/New_York"));
        pojo.zonedDateTime = ZonedDateTime.of(2016, 5,18,9,0,0,0,ZoneId.of("UTC"));
        pojo.simpleDate = new Date(Date.parse("Sat, 12 Aug 1995 13:30:00 GMT"));
        return pojo;
    }

    public static class SimplePojo {
        public String paramString;
        public Long paramLong;
        public boolean paramBoolean;
        public LocalDateTime localDateTime;
        public ZonedDateTime zonedDateTime;
        public Date simpleDate;
    }

}