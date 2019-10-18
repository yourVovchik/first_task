package com.balinasoft.firsttask.config.swagger.plugin;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import org.hibernate.validator.constraints.NotBlank;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

import javax.validation.constraints.NotNull;

public class NotBlankOrNotNullAnnotationPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    @Override
    public void apply(ModelPropertyContext context) {
        AnnotatedField field = context.getBeanPropertyDefinition().get().getField();
        if (field == null) {
            return;
        }
        boolean required = field.hasAnnotation(NotNull.class) || field.hasAnnotation(NotBlank.class);
        context.getBuilder().required(required);
    }

}
