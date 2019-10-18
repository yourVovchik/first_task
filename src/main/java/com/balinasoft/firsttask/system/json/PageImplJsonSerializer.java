package com.balinasoft.firsttask.system.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class PageImplJsonSerializer extends JsonSerializer<PageImpl> {

    @Override
    public void serialize(PageImpl page, JsonGenerator generator, SerializerProvider provider) throws IOException {
        Map<String, Object> data = new LinkedHashMap<>(5, 1);
        data.put("page", page.getNumber());
        data.put("pageSize", page.getSize());
        data.put("totalPages", page.getTotalPages());
        data.put("totalElements", page.getTotalElements());
        data.put("content", page.getContent());
        generator.writeObject(data);
    }

}
