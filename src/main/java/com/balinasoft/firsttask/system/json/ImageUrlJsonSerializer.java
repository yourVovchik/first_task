package com.balinasoft.firsttask.system.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ImageUrlJsonSerializer extends JsonSerializer<String> {

    private static String BASE_URL;

    public ImageUrlJsonSerializer() {
    }

    @Autowired
    public ImageUrlJsonSerializer(@Value("${project.url}") String url) {
        BASE_URL = url;
    }

    @Override
    public void serialize(String value, JsonGenerator gen,
                          SerializerProvider serializers) throws IOException {
        if (value != null && !value.isEmpty()) {
            gen.writeObject(BASE_URL + "/images/" + value);
        } else {
            gen.writeObject(value);
        }
    }

    @SuppressWarnings("unused")
    public static String toFull(String value) {
        if (value != null && !value.isEmpty()) {
            return BASE_URL + "/images/" + value;
        } else {
            return value;
        }
    }
}
