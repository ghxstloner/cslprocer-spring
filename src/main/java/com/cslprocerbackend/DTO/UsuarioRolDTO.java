package com.cslprocerbackend.DTO;

import com.cslprocerbackend.model.UsuarioRol;
import lombok.Data;

/**
 * La clase UsuarioRolDTO es un objeto de transferencia de datos para la entidad UsuarioRol.
 * Se utiliza para transferir datos de UsuarioRol entre diferentes capas de la aplicación.
 */
@Data
public class UsuarioRolDTO {

    /**
     * Identificador único del rol.
     */
    private Long id;

    /**
     * Nombre del rol.
     */
    private String nombre;

    /**
     * Constructor vacío necesario para la serialización/deserialización.
     */
    public UsuarioRolDTO() {
    }

    /**
     * Constructor que inicializa el UsuarioRolDTO con los datos de un objeto UsuarioRol.
     *
     * @param usuarioRol el objeto UsuarioRol del cual se obtendrán los datos
     */
    public UsuarioRolDTO(UsuarioRol usuarioRol) {
        this.id = usuarioRol.getId();
        this.nombre = usuarioRol.getNombre();
    }

    /**
     * Constructor que inicializa el UsuarioRolDTO con un id y un nombre.
     *
     * @param id el identificador único del rol
     * @param nombre el nombre del rol
     */
    public UsuarioRolDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}