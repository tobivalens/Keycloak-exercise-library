package co.analisys.biblioteca.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Biblioteca - Microservicio de Notificación",
                version = "1.0",
                description = "Envío de notificaciones a usuarios. Protegido con JWT (Keycloak).",
                contact = @Contact(name = "Proyecto Biblioteca")
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Pega aquí el access_token obtenido de Keycloak (sin el prefijo 'Bearer ')"
)
public class OpenApiConfig {
}
