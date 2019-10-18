package com.balinasoft.firsttask.config.swagger.plugin;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

import java.lang.reflect.Field;

public class ModelFieldOrderPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    @Override
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void apply(ModelPropertyContext context) {
        AnnotatedField field = context.getBeanPropertyDefinition().get().getField();
        if (field == null) {
            return;
        }

        int position = 0;

        Field[] declaredFields = field.getDeclaringClass().getDeclaredFields();
        for (int i = 0, declaredFieldsLength = declaredFields.length; i < declaredFieldsLength; i++) {
            Field declaredField = declaredFields[i];
            if (declaredField.getName().equals(field.getName())) {
                position = i + 1;
            }
        }


        context.getBuilder().position(position);
    }

}
