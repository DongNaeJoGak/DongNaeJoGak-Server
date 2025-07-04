package com.example.DongNaeJoGak.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
//                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
//                .components(new Components().addSecuritySchemes("BearerAuth",
//                        new SecurityScheme()
//                                .type(SecurityScheme.Type.HTTP)
//                                .scheme("bearer")
//                                .bearerFormat("JWT")
//                ))
                .info(new Info()
                        .title("DongNaeJoGak API")
                        .description("DongNaeJoGak API 명세서 with JWT Auth")
                        .version("v1.0"));
    }
}