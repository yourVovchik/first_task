package com.balinasoft.firsttask.system.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UnixTimestampDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String unixTimestamp = p.getText().trim();
        return new Date(TimeUnit.SECONDS.toMillis(Long.valueOf(unixTimestamp)));
    }
}