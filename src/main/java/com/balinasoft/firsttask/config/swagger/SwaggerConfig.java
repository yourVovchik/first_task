package com.balinasoft.firsttask.config.swagger;

import com.balinasoft.firsttask.config.swagger.plugin.*;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.bean.validators.plugins.schema.DecimalMinMaxAnnotationPlugin;
import springfox.bean.validators.plugins.schema.MinMaxAnnotationPlugin;
import springfox.bean.validators.plugins.schema.PatternAnnotationPlugin;
import springfox.bean.validators.plugins.schema.SizeAnnotationPlugin;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.not;
import static java.util.Collections.emptyList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final TypeNameExtractor nameExtractor;

    private final TypeResolver typeResolver;

    @Autowired
    public SwaggerConfig(TypeNameExtractor nameExtractor, TypeResolver typeResolver) {
        this.nameExtractor = nameExtractor;
        this.typeResolver = typeResolver;
    }

    @Bean
    //custom
    public NotBlankOrNotNullAnnotationPlugin notBlankOrNotNullAnnotationPlugin() {
        return new NotBlankOrNotNullAnnotationPlugin();
    }

    @Bean
    //custom
    public PageableParameterBuilderPlugin pageableParameterBuilderPlugin() {
        return new PageableParameterBuilderPlugin(nameExtractor, typeResolver);
    }

    @Bean
    //custom
    public ModelFieldOrderPlugin orderPlugin() {
        return new ModelFieldOrderPlugin();
    }

    @Bean
    //custom
    public SecurityAnnotationPlugin securityAnnotationPlugin() {
        return new SecurityAnnotationPlugin();
    }

    @Bean
    //custom
    public PagePlugin pagePlugin() {
        return new PagePlugin();
    }

    @Bean
    public MinMaxAnnotationPlugin minMaxAnnotationPlugin() {
        return new MinMaxAnnotationPlugin();
    }

    @Bean
    public PatternAnnotationPlugin patternAnnotationPlugin() {
        return new PatternAnnotationPlugin();
    }

    @Bean
    public SizeAnnotationPlugin sizeAnnotationPlugin() {
        return new SizeAnnotationPlugin();
    }

    @Bean
    public DecimalMinMaxAnnotationPlugin decimalMinMaxAnnotationPlugin() {
        return new DecimalMinMaxAnnotationPlugin();
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .displayRequestDuration(true)
                .validatorUrl("")
                .defaultModelExpandDepth(10)
                .defaultModelsExpandDepth(10)
                .build();
    }

    @Bean
    public Docket docket1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api1")
                .apiInfo(new ApiInfo("", "", "", "", new Contact("", "", ""), "", "", emptyList()))
                .useDefaultResponseMessages(false)
                .select()
                .paths(not(PathSelectors.regex("/error.*")))
                .paths(not(PathSelectors.regex("/api/v2.*")))
                .build()
                .ignoredParameterTypes(ApiIgnore.class)
                ;
    }

    @Bean
    public Docket docket2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api2")
                .apiInfo(new ApiInfo("", "", "", "", new Contact("", "", ""), "", "", emptyList()))
                .useDefaultResponseMessages(false)
                .select()
                .paths(PathSelectors.regex("/api/v2.*"))
                .build()
                .ignoredParameterTypes(ApiIgnore.class)
                ;
    }

}
