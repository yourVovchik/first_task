package com.balinasoft.firsttask.config.swagger;

import org.springframework.core.annotation.Order;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class SecurityAnnotationPlugin implements OperationBuilderPlugin {

    private ParameterBuilder parameterBuilder = new ParameterBuilder();

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    @Override
    public void apply(OperationContext context) {

        Secured annotation = context.getHandlerMethod().getMethodAnnotation(Secured.class);
        if (annotation == null) {
            return;
        }

        String[] roles = annotation.value();
        for (int i = 0; i < roles.length; i++) {
            roles[i] = roles[i].replace("ROLE_", "");
        }

        String description = "Roles: " + Arrays.toString(roles);

        List<Parameter> parameters = new LinkedList<>();
        parameters.add(parameterBuilder
                .name("Access-Token")
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(true)
                .description(description)
                .build()
        );
        context.operationBuilder().parameters(parameters);
    }
}
