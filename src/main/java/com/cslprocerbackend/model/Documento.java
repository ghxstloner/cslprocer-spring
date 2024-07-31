package com.cslprocerbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "documento")
@Data
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocumento;

    private String nombre;
    private String url;
    private String tipo;
}
