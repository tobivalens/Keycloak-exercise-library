package co.analisys.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Microservicio: notificacion
 *
 * Reglas de autorización:
 *  - POST /notificar            -> ADMIN o BIBLIOTECARIO (normalmente invocado por el servicio de circulación)
 *  - Swagger / H2 (desarrollo)  -> público
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
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(RUTAS_PUBLICAS).permitAll()
                        .requestMatchers("/notificar/**").hasAnyRole("ADMIN", "BIBLIOTECARIO")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new KeycloakRealmRoleConverter()))
                );

        return http.build();
    }
}
