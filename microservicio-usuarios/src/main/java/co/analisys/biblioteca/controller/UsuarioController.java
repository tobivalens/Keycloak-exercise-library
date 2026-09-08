package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.model.Email;
import co.analisys.biblioteca.model.Usuario;
import co.analisys.biblioteca.model.UsuarioId;
import co.analisys.biblioteca.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones sobre usuarios de la biblioteca. Requiere rol ADMIN o BIBLIOTECARIO.")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un usuario por id", description = "Requiere rol ADMIN o BIBLIOTECARIO.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o expirado"),
            @ApiResponse(responseCode = "403", description = "El usuario autenticado no tiene el rol requerido"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public Usuario obtenerUsuario(@Parameter(description = "Identificador del usuario") @PathVariable String id) {
        return usuarioService.obtenerUsuario(new UsuarioId(id));
    }

    @PutMapping("/{id}/email")
    @Operation(summary = "Cambiar el email de un usuario", description = "Requiere rol ADMIN o BIBLIOTECARIO.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email actualizado"),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o expirado"),
            @ApiResponse(responseCode = "403", description = "El usuario autenticado no tiene el rol requerido")
    })
    public void cambiarEmail(@PathVariable String id, @RequestBody String nuevoEmail) {
        usuarioService.cambiarEmailUsuario(new UsuarioId(id), new Email(nuevoEmail));
    }
}
