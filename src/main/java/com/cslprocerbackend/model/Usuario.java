package com.cslprocerbackend.model;

import jakarta.persistence.*;

import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nombre;
    private String email;
    private String telefono;
    private String password;
}
