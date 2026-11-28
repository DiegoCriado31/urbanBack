package com.urbanstyle.urbanback.usuarios.controller;

import com.urbanstyle.urbanback.security.JwtUtil;
import com.urbanstyle.urbanback.usuarios.model.Rol;
import com.urbanstyle.urbanback.usuarios.model.usuarioModel;
import com.urbanstyle.urbanback.usuarios.service.usuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestión de cuentas, autenticación JWT y roles")
public class usuarioController {

    @Autowired
    private usuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    private Map<String, Object> construirRespuestaConToken(usuarioModel u) {
        String token = jwtUtil.generarToken(u.getEmail(), u.getRol().name());

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("token", token);
        respuesta.put("id", u.getId());
        respuesta.put("run", u.getRun());
        respuesta.put("nombre", u.getNombre());
        respuesta.put("email", u.getEmail());
        respuesta.put("rol", u.getRol());
        respuesta.put("direccion", u.getDireccion());
        return respuesta;
    }

    @Operation(summary = "Listar todos los usuarios", description = "Requiere rol SUPERVISOR.")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios registrados")
    @GetMapping
    public List<usuarioModel> obtenerUsuarios() {
        return usuarioService.listarTodos();
    }

    @Operation(summary = "Registrar un nuevo usuario", description = "Crea una cuenta con rol CLIENTE por defecto y devuelve un token JWT.")
    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos o correo/RUN ya registrado", content = @Content)
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody usuarioModel usuario) {
        try {
            usuarioModel creado = usuarioService.registrar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(construirRespuestaConToken(creado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Iniciar sesión", description = "Valida credenciales y devuelve los datos del usuario junto con su token JWT.")
    @ApiResponse(responseCode = "200", description = "Autenticación exitosa")
    @ApiResponse(responseCode = "401", description = "Correo o contraseña incorrectos", content = @Content)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        Optional<usuarioModel> usuario = usuarioService.autenticar(email, password);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(construirRespuestaConToken(usuario.get()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Correo o contraseña incorrectos."));
    }

    @Operation(summary = "Cambiar el rol de un usuario", description = "Permite asignar CLIENTE, COMERCIAL o SUPERVISOR. Requiere rol SUPERVISOR.")
    @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente")
    @ApiResponse(responseCode = "400", description = "Rol inválido o usuario no encontrado", content = @Content)
    @PatchMapping("/{id}/rol")
    public ResponseEntity<?> cambiarRol(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            Rol nuevoRol = Rol.valueOf(body.get("rol").toUpperCase());
            usuarioModel actualizado = usuarioService.actualizarRol(id, nuevoRol);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina permanentemente un usuario por su ID. Requiere rol SUPERVISOR.")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
