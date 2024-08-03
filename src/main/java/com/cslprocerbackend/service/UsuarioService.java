package com.cslprocerbackend.service;

import com.cslprocerbackend.DTO.UsuarioRegistroDTO;
import com.cslprocerbackend.DTO.UsuarioResponseDTO;
import com.cslprocerbackend.DTO.UsuarioUpdateDTO;
import com.cslprocerbackend.exception.UsuarioYaExisteException;
import com.cslprocerbackend.model.UsuarioRol;
import com.cslprocerbackend.model.Usuario;
import com.cslprocerbackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private UsuarioRol roles;

    public UsuarioResponseDTO registrarUsuario(UsuarioRegistroDTO usuarioRegistroDTO) throws UsuarioYaExisteException {
        if (usuarioRepository.existsByEmail(usuarioRegistroDTO.getEmail())) {
            throw new UsuarioYaExisteException("El usuario ya existe");
        }
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioRegistroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioRegistroDTO.getPassword()));
        // Set other fields as necessary
        usuarioRepository.save(usuario);
        return new UsuarioResponseDTO();
    }

    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateDTO usuarioUpdateDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (!usuarioOpt.isPresent()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setEmail(usuarioUpdateDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioUpdateDTO.getPassword()));
        // Set other fields as necessary
        usuarioRepository.save(usuario);
        return new UsuarioResponseDTO();
    }

    public void deleteUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(), new ArrayList<>());
    }

    public void asignarRol(Long id, String rol) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (!usuarioOpt.isPresent()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        Usuario usuario = usuarioOpt.get();

        // Verifica si el rol ya está asignado si no quieres duplicados
        if (!usuario.getRoles().contains(rol)) usuario.getRoles().add(roles); // Asigna el nuevo rol
        usuarioRepository.save(usuario); // Guarda el usuario con el rol actualizado
    }
}
