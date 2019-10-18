package com.balinasoft.firsttask.config.swagger.plugin;

import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import org.springframework.data.domain.Page;
import springfox.documentation.builders.ModelPropertyBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

public class PagePlugin implements ModelPropertyBuilderPlugin {

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    @Override
    public void apply(ModelPropertyContext context) {
        BeanPropertyDefinition propertyDefinition = context.getBeanPropertyDefinition().get();
        Class<?> clazz = propertyDefinition.getGetter().getMember().getDeclaringClass();

        if (clazz.isAssignableFrom(Page.class)) {
            String name = propertyDefinition.getName();
            ModelPropertyBuilder builder = context.getBuilder();

            switch (name) {
                case "number":
                    builder.name("page");
                    builder.example(1);
                    break;
                case "size":
                    builder.name("pageSize");
                    builder.example(20);
                    break;
                case "totalPages":
                    builder.example(2);
                    break;
                case "totalElements":
                    builder.example(33);
                    break;
                case "content":
                    break;
                default:
                    builder.isHidden(true);
            }
        }
    }

}
