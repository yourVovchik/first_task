package com.balinasoft.firsttask.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebResourceConfig extends WebMvcConfigurerAdapter {

    @Value("${project.image-folder}")
    private String imageFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations("file:" + imageFolder + "/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS));
    }
}
