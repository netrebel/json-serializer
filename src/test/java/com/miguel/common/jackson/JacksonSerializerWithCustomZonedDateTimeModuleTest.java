package com.miguel.common.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.time.*;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertEquals;

/**
 * @author miguel.reyes on 3/31/16.
 */
public class JacksonSerializerWithCustomZonedDateTimeModuleTest {

    /**
     * Match regex pattern: ^\{.*\.\d\d\dZ\".*\}$
     */
    private final Matcher<String> matcher = matchesPattern("^\\{.*\\.\\d\\d\\dZ\".*\\}$");
    private ObjectMapper objectMapper = JacksonSerializer.createMapperWithCustomSerializer();
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
        String json = "{\"param_string\":\"hello\",\"param_long\":1234,\"param_boolean\":true,\"zoned_date_time\":\"2016-03-28T21:10:12.326Z\",\"simple_date\":\"2016-03-28T21:10:12.326Z\"}";
        System.out.println("Deserialize json = " + json);
        SimplePojo pojoFromJson = objectMapper.readValue(json, SimplePojo.class);
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

//        pojo.localDateTime = LocalDateTime.now(ZoneId.of("America/New_York"));
        pojo.zonedDateTime = ZonedDateTime.now(Clock.systemUTC());
        pojo.simpleDate = Date.from(Instant.now(Clock.systemUTC()));
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