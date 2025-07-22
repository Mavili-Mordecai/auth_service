package com.locus_narrative.auth_service.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Auth Service API",
                description = "API for user authentication.",
                version = "1.0.0"
        )
)
public class OpenAPIConfig {
}
