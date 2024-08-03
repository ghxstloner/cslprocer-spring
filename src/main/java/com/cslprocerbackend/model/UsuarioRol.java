package com.cslprocerbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * La clase UsuarioRol representa un rol en el sistema.
 * Un rol tiene un identificador único y un nombre.
 * Esta clase utiliza JPA para el mapeo de objetos relacionales y Lombok para
 * generar automáticamente los métodos getter, setter, toString, equals y hashCode.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRol {

    /**
     * Identificador único del rol.
     * Se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del rol.
     * Debe ser único.
     */
    @Column(unique = true)
    private String nombre;

    /**
     * Constructor que inicializa el rol con un nombre.
     *
     * @param nombre el nombre del rol
     */
    public UsuarioRol(String nombre) {
        this.nombre = nombre;
    }

    // Otros atributos como descripción, permisos, etc.
}