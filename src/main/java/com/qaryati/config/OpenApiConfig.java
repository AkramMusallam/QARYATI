package com.qaryati.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI qaryatiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Qaryati - Palestinian Villages Knowledge Platform API")
                        .description("API-centric backend platform for organizing reliable, structured information about Palestinian villages, historical names, population records, heritage landmarks, agriculture, and public facilities.")
                        .version("v1.0.0")
                        .contact(new Contact().name("Qaryati Engineering Team")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Provide the JWT token obtained from POST /api/v1/auth/login")));
    }
}
