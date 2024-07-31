package com.cslprocerbackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "informe_auditoria")
@Data
public class InformeAuditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInforme;

    private String descripcion;
    private String fecha;
}
