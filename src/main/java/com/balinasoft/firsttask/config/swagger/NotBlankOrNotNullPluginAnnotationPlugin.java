package com.balinasoft.firsttask.config.swagger;


import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.bean.validators.plugins.BeanValidators;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

import javax.validation.constraints.NotNull;

import static springfox.bean.validators.plugins.BeanValidators.validatorFromBean;
import static springfox.bean.validators.plugins.BeanValidators.validatorFromField;

@Component
@Order(BeanValidators.BEAN_VALIDATOR_PLUGIN_ORDER)
public class NotBlankOrNotNullPluginAnnotationPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public boolean supports(DocumentationType delimiter) {
        // we simply support all documentationTypes!
        return true;
    }

    @Override
    public void apply(ModelPropertyContext context) {
        Optional<NotBlank> notBlank = extractNotBlankAnnotation(context);
        Optional<NotNull> notNull = extractNotNullAnnotation(context);
        context.getBuilder().required(notBlank.isPresent() || notNull.isPresent());
    }

    @VisibleForTesting
    Optional<NotNull> extractNotNullAnnotation(ModelPropertyContext context) {
        return validatorFromBean(context, NotNull.class)
                .or(validatorFromField(context, NotNull.class));
    }

    @VisibleForTesting
    Optional<NotBlank> extractNotBlankAnnotation(ModelPropertyContext context) {
        return validatorFromBean(context, NotBlank.class)
                .or(validatorFromField(context, NotBlank.class));
    }

}
