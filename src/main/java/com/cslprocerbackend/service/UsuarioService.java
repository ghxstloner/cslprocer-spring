package com.cslprocerbackend.service;

import com.cslprocerbackend.model.Usuario;
import com.cslprocerbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registerUsuario(String nombre, String email, String telefono, String password) throws Exception {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new Exception("El correo electrónico ya está en uso.");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        usuario.setPassword(passwordEncoder.encode(password));
        return usuarioRepository.save(usuario);
    }

    public Usuario loginUsuario(String email, String password) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("No se encontró al usuario."));
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new Exception("Las credenciales de inicio de sesión son inválidas.");
        }
        return usuario;
    }

    /**
     * @param email
     * @return Esto está retornando el usuario obteniendo como parámetro principal el
     * Correo electrónico
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.withUsername(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities("USER")
                .build();
    }

    public void updatePassword(Long id, String newPassword) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setPassword(newPassword);
        usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> getAllUsuarios() {
        return new ArrayList<>(usuarioRepository.findAll());
    }
}
