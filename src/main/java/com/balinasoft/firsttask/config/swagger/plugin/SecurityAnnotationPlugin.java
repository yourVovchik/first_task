package com.balinasoft.firsttask.config.swagger.plugin;

import com.google.common.base.Optional;
import org.springframework.security.access.annotation.Secured;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SecurityAnnotationPlugin implements OperationBuilderPlugin {

    private ParameterBuilder parameterBuilder = new ParameterBuilder();

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }

    @Override
    public void apply(OperationContext context) {

        Optional<Secured> annotation = context.findAnnotation(Secured.class);
        if (annotation.isPresent()) {
            boolean required = true;

            String[] roles = annotation.get().value();
            for (int i = 0; i < roles.length; i++) {
                if (roles[i].equals("IS_AUTHENTICATED_ANONYMOUSLY")) {
                    required = false;
                    roles[i] = "ANONYM";
                } else {
                    roles[i] = roles[i].replace("ROLE_", "");
                }
            }
            String description = "Roles: " + Arrays.toString(roles);

            List<Parameter> parameters = new LinkedList<>();
            parameters.add(parameterBuilder
                    .name("Access-Token")
                    .modelRef(new ModelRef("String"))
                    .parameterType("header")
                    .required(required)
                    .description(description)
                    .build()
            );
            context.operationBuilder().parameters(parameters);
        }

    }
}
