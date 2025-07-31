package com.rivaldy.orderapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(info());
    }

    public Info info(){
        return new Info()
                .title("REST API for Order App")
                .version("1.0")
                .description("Provide REST API for Simple Order App");
    }
}
