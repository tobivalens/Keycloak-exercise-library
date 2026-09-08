package co.analisys.biblioteca.exception;

import co.analisys.biblioteca.model.PrestamoId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404: no existe préstamo con ese id
public class PrestamoNoEncontradoException extends RuntimeException {

    public PrestamoNoEncontradoException(PrestamoId prestamoId) {
        super("No se encontró el préstamo con ID " + prestamoId.getPrestamoid_value());
    }
}
