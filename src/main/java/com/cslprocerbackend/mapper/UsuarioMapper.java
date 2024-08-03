package com.cslprocerbackend.mapper;

import com.cslprocerbackend.DTO.UsuarioRegistroDTO;
import com.cslprocerbackend.DTO.UsuarioResponseDTO;
import com.cslprocerbackend.DTO.UsuarioUpdateDTO;
import com.cslprocerbackend.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRegistroDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        // No establecemos la contraseña aquí, se manejará en el servicio
        return usuario;
    }

    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        return dto;
    }

    public void updateEntityFromDTO(UsuarioUpdateDTO dto, Usuario usuario) {
        if (dto.getNombre() != null) {
            usuario.setNombre(dto.getNombre());
        }
        if (dto.getEmail() != null) {
            usuario.setEmail(dto.getEmail());
        }
        if (dto.getTelefono() != null) {
            usuario.setTelefono(dto.getTelefono());
        }
        // No actualizamos la contraseña aquí, se manejará en el servicio
    }
}
