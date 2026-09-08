package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.model.Libro;
import co.analisys.biblioteca.model.LibroId;
import co.analisys.biblioteca.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
@Tag(name = "Catálogo", description = "Consulta y gestión de libros de la biblioteca.")
public class CatalogoController {
    private final CatalogoService catalogoService;

    @Autowired
    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un libro por id", description = "Cualquier usuario autenticado (ADMIN, BIBLIOTECARIO o USUARIO).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o expirado")
    })
    public Libro obtenerLibro(@PathVariable String id) {
        return catalogoService.obtenerLibro(new LibroId(id));
    }

    @GetMapping("/{id}/disponible")
    @Operation(summary = "Consultar disponibilidad de un libro", description = "Cualquier usuario autenticado.")
    public boolean isLibroDisponible(@PathVariable String id) {
        Libro libro = catalogoService.obtenerLibro(new LibroId(id));
        return libro != null && libro.isDisponible();
    }

    @PutMapping("/{id}/disponibilidad")
    @Operation(summary = "Actualizar disponibilidad de un libro", description = "Requiere rol ADMIN o BIBLIOTECARIO.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Disponibilidad actualizada"),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o expirado"),
            @ApiResponse(responseCode = "403", description = "El usuario autenticado no tiene el rol requerido")
    })
    public void actualizarDisponibilidad(@PathVariable String id, @RequestBody boolean disponible) {
        catalogoService.actualizarDisponibilidad(new LibroId(id), disponible);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar libros por criterio (título, autor, categoría)", description = "Cualquier usuario autenticado.")
    public List<Libro> buscarLibros(@RequestParam String criterio) {
        return catalogoService.buscarLibros(criterio);
    }
}
