package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.model.LibroId;
import co.analisys.biblioteca.model.Prestamo;
import co.analisys.biblioteca.model.PrestamoId;
import co.analisys.biblioteca.model.UsuarioId;
import co.analisys.biblioteca.service.CirculacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/circulacion")
@Tag(name = "Circulación", description = "Préstamos y devoluciones de libros. Requiere rol ADMIN o BIBLIOTECARIO.")
public class CirculacionController {
    @Autowired
    private CirculacionService circulacionService;

    @PostMapping("/prestar")
    @Operation(summary = "Registrar el préstamo de un libro a un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Préstamo registrado"),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o expirado"),
            @ApiResponse(responseCode = "403", description = "El usuario autenticado no tiene el rol requerido")
    })
    public void prestarLibro(@RequestParam String usuarioId, @RequestParam String libroId) {
        circulacionService.prestarLibro(new UsuarioId(usuarioId), new LibroId(libroId));
    }

    @PostMapping("/devolver")
    @Operation(summary = "Registrar la devolución de un préstamo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Devolución registrada"),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o expirado"),
            @ApiResponse(responseCode = "403", description = "El usuario autenticado no tiene el rol requerido")
    })
    public void devolverLibro(@RequestParam String prestamoId) {
        circulacionService.devolverLibro(new PrestamoId(prestamoId));
    }

    @GetMapping("/prestamos")
    @Operation(summary = "Listar todos los préstamos registrados")
    public List<Prestamo> obtenerTodosPrestamos() {
        return circulacionService.obtenerTodosPrestamos();
    }
}
