package com.contractEmployee.contractEmployee.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(new Info()
                .title("JWT Auth API")
                .version("v1")
                .description("Spring Boot 3 JWT Authentication"));
    }
}
