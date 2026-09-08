package co.analisys.biblioteca.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Convierte el token JWT emitido por Keycloak en un Authentication de Spring Security.
 *
 * Keycloak coloca los roles de "realm" dentro del claim:
 *   "realm_access": { "roles": ["ADMIN", "BIBLIOTECARIO", "USUARIO"] }
 *
 * Spring Security espera que las autoridades tengan el prefijo "ROLE_" para que
 * funcionen los métodos hasRole()/hasAnyRole(). Este converter hace esa traducción.
 */
public class KeycloakRealmRoleConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter defaultConverter = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                defaultConverter.convert(jwt).stream(),
                extractRealmRoles(jwt).stream()
        ).collect(Collectors.toSet());

        return new org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken(
                jwt, authorities, extractPrincipalName(jwt));
    }

    private String extractPrincipalName(Jwt jwt) {
        String preferredUsername = jwt.getClaimAsString("preferred_username");
        return preferredUsername != null ? preferredUsername : jwt.getSubject();
    }

    @SuppressWarnings("unchecked")
    private Collection<GrantedAuthority> extractRealmRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null || realmAccess.get("roles") == null) {
            return Collections.emptyList();
        }
        List<String> roles = (List<String>) realmAccess.get("roles");
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}
