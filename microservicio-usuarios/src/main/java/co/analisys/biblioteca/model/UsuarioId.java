package co.analisys.biblioteca.model;

import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor(force = true)  // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all fields as arguments
public class UsuarioId {

    private final String usuario_value;
    // constructor y métodos
}
