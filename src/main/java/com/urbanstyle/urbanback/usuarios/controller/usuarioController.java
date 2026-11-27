package com.urbanstyle.urbanback.usuarios.controller;

import com.urbanstyle.urbanback.usuarios.model.Rol;
import com.urbanstyle.urbanback.usuarios.model.usuarioModel;
import com.urbanstyle.urbanback.usuarios.service.usuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class usuarioController {
    @Autowired
    private usuarioService usuarioService;

    @GetMapping
    public List<usuarioModel> obtenerUsuarios(){
        return usuarioService.listarTodos();
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody usuarioModel usuario) {
        try {
            usuarioModel creado = usuarioService.registrar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        Optional<usuarioModel> usuario = usuarioService.autenticar(email, password);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Correo o contraseña incorrectos."));
    }
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

