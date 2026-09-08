package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.dto.NotificacionDTO;
import co.analisys.biblioteca.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificar")
@Tag(name = "Notificaciones", description = "Envío de notificaciones. Requiere rol ADMIN o BIBLIOTECARIO.")
public class NotificacionController {
    @Autowired
    private NotificacionService notificacionService;

    @PostMapping
    @Operation(summary = "Enviar una notificación a un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notificación enviada"),
            @ApiResponse(responseCode = "401", description = "Token ausente, inválido o expirado"),
            @ApiResponse(responseCode = "403", description = "El usuario autenticado no tiene el rol requerido")
    })
    public void enviarNotificacion(@RequestBody NotificacionDTO notificacion) {
        notificacionService.enviarNotificacion(notificacion);
    }
}