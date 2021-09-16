package com.bookeroo.microservice.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.bookeroo.microservice.login.security.SecurityConstant.HEADER_KEY;

@Configuration
public class WebConfigurer {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                WebMvcConfigurer.super.addCorsMappings(registry);
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("PUT", "DELETE")
                        .allowedHeaders(HEADER_KEY)
                        .exposedHeaders(HEADER_KEY)
                        .allowCredentials(false).maxAge(3600);
            }
        };
    }
}
