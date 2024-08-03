package com.cslprocerbackend.controller;

import com.cslprocerbackend.DTO.UsuarioLoginDTO;
import com.cslprocerbackend.DTO.UsuarioRegistroDTO;
import com.cslprocerbackend.DTO.UsuarioResponseDTO;
import com.cslprocerbackend.DTO.UsuarioUpdateDTO;
import com.cslprocerbackend.exception.UsuarioYaExisteException;
import com.cslprocerbackend.service.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para gestionar la autenticación y el registro de usuarios.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Constructor para AuthController.
     *
     * @param usuarioService el servicio de usuario.
     * @param authenticationManager el administrador de autenticación.
     * @param jwtTokenProvider el proveedor de tokens JWT.
     */
    public AuthController(UsuarioService usuarioService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param usuarioRegistroDTO los datos del usuario a registrar.
     * @return una respuesta con el usuario registrado o un error.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioRegistroDTO usuarioRegistroDTO) {
        try {
            UsuarioResponseDTO usuarioResponseDTO = usuarioService.registrarUsuario(usuarioRegistroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponseDTO);
        } catch (UsuarioYaExisteException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Error interno\"}");
        }
    }

    /**
     * Autentica a un usuario.
     *
     * @param usuarioLoginDTO los datos de inicio de sesión del usuario.
     * @return una respuesta con el token JWT o un error.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuarioLoginDTO.getEmail(),
                            usuarioLoginDTO.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new com.cslprocerbackend.Response.JwtAuthenticationResponse(jwt));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Credenciales inválidas\"}");
        }
    }

    /**
     * Actualiza un usuario.
     *
     * @param id el ID del usuario a actualizar.
     * @param usuarioUpdateDTO los nuevos datos del usuario.
     * @return una respuesta con el usuario actualizado o un error.
     */
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        try {
            UsuarioResponseDTO usuarioResponseDTO = usuarioService.actualizarUsuario(id, usuarioUpdateDTO);
            return ResponseEntity.ok(usuarioResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Elimina un usuario.
     *
     * @param id el ID del usuario a eliminar.
     * @return una respuesta con un mensaje de confirmación o un error.
     */
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok("{\"message\": \"Usuario eliminado\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Asigna un rol a un usuario.
     *
     * @param id el ID del usuario.
     * @param rol el rol a asignar.
     * @return una respuesta sin contenido.
     */
    @PutMapping("/{id}/roles/{rol}")
    public ResponseEntity<Void> asignarRol(@PathVariable Long id, @PathVariable String rol) {
        usuarioService.asignarRol(id, rol);
        return ResponseEntity.noContent().build();
    }
}

/**
 * Proveedor de tokens JWT.
 */
@Component
class JwtTokenProvider {

    private static final String JWT_SECRET = "your_secret_key";
    private static final long JWT_EXPIRATION_MILLIS = 604800000L; // 1 semana

    /**
     * Genera un token JWT para un usuario autenticado.
     *
     * @param authentication el objeto de autenticación del usuario.
     * @return el token JWT generado.
     */
    public String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MILLIS))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
}
