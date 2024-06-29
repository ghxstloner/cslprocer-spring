package com.cslprocerbackend.controller;

import com.cslprocerbackend.model.Usuario;
import com.cslprocerbackend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioService.registerUsuario(
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getTelefono(),
                    usuario.getPassword());
            return ResponseEntity.ok("{\"message\": \"Registro exitoso\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioService.loginUsuario(
                    usuario.getEmail(),
                    usuario.getPassword());
            return ResponseEntity.ok("{\"message\": \"Ingreso exitoso\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
