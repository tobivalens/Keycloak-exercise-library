package co.analisys.biblioteca.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

/**
 * Reenvía el header "Authorization: Bearer <jwt>" de la petición entrante hacia las
 * llamadas salientes que circulacion-service hace a catalogo-service y notificacion-service.
 *
 * Sin este interceptor, RestTemplate hace esas llamadas sin token y, como esos endpoints
 * también están protegidos por Spring Security + Keycloak, responden 401 Unauthorized:
 * el flujo de "prestar" y "devolver" se rompe en producción aunque el usuario tenga
 * un token válido, porque el propio microservicio nunca lo propaga.
 */
public class BearerTokenForwardingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        Object attributes = RequestContextHolder.getRequestAttributes();
        if (attributes instanceof ServletRequestAttributes servletAttributes) {
            HttpServletRequest currentRequest = servletAttributes.getRequest();
            String authorization = currentRequest.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorization != null && !request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                request.getHeaders().add(HttpHeaders.AUTHORIZATION, authorization);
            }
        }
        return execution.execute(request, body);
    }
}
