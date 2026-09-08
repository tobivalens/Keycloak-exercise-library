package co.analisys.biblioteca.exception;

import co.analisys.biblioteca.model.LibroId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // 409: el recurso existe pero no puede prestarse ahora
public class LibroNoDisponibleException extends RuntimeException {

    public LibroNoDisponibleException(LibroId libroId) {
        super("El libro con ID " + libroId.getLibroid_value() + " no está disponible.");
    }
}
