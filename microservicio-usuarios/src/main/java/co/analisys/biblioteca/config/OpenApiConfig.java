package co.analisys.biblioteca.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * Documentación OpenAPI para el microservicio de Usuarios.
 * Agrega el botón "Authorize" en Swagger UI para pegar un token Bearer (JWT de Keycloak)
 * y probar los endpoints protegidos directamente desde la interfaz.
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Biblioteca - Microservicio de Usuarios",
                version = "1.0",
                description = "Gestión de usuarios de la biblioteca. Protegido con JWT (Keycloak).",
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
