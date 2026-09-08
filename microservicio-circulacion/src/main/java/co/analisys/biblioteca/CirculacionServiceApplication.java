package co.analisys.biblioteca;

import co.analisys.biblioteca.config.BearerTokenForwardingInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CirculacionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CirculacionServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		// Propaga el JWT de la petición entrante a las llamadas hacia catalogo-service
		// y notificacion-service, que también validan el token (ver BearerTokenForwardingInterceptor).
		restTemplate.getInterceptors().add(new BearerTokenForwardingInterceptor());
		return restTemplate;
	}
}
