package co.analisys.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Microservicio: usuarios
 *
 * Reglas de autorización:
 *  - Endpoints públicos: documentación Swagger y consola H2 (solo para desarrollo).
 *  - GET  /usuarios/{id}        -> ADMIN o BIBLIOTECARIO
 *  - PUT  /usuarios/{id}/email  -> ADMIN o BIBLIOTECARIO
 *  - Cualquier otra ruta        -> requiere estar autenticado
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] RUTAS_PUBLICAS = {
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/h2-console/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // API REST sin estado: no se usan sesiones, cada request trae su propio JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // CSRF no aplica a APIs stateless protegidas con JWT
                .csrf(AbstractHttpConfigurer::disable)
                // Necesario para poder ver la consola H2 embebida en un iframe (solo desarrollo)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(RUTAS_PUBLICAS).permitAll()
                        .requestMatchers("/usuarios/**").hasAnyRole("ADMIN", "BIBLIOTECARIO")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new KeycloakRealmRoleConverter()))
                );

        return http.build();
    }
}
