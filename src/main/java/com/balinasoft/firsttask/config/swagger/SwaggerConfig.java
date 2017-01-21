package com.balinasoft.firsttask.config.swagger;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.bean.validators.plugins.MinMaxAnnotationPlugin;
import springfox.bean.validators.plugins.PatternAnnotationPlugin;
import springfox.bean.validators.plugins.SizeAnnotationPlugin;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public MinMaxAnnotationPlugin minMaxPlugin() {
        return new MinMaxAnnotationPlugin();
    }

    @Bean
    public SizeAnnotationPlugin sizePlugin() {
        return new SizeAnnotationPlugin();
    }

    @Bean
    public PatternAnnotationPlugin patternPlugin() {
        return new PatternAnnotationPlugin();
    }

    @Bean
    public NotBlankOrNotNullPluginAnnotationPlugin notBlankOrNotNullPlugin() {
        return new NotBlankOrNotNullPluginAnnotationPlugin();
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select().paths(paths()).build()

                .ignoredParameterTypes(ApiIgnore.class)
                ;
    }

    private Predicate<String> paths() {
        return Predicates.not(PathSelectors.regex("/error.*"));
    }
}
