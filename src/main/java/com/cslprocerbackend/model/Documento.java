package com.cslprocerbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * Entidad que representa un documento en el sistema.
 * Esta clase utiliza JPA para el mapeo de objetos relacionales y Lombok para
 * generar automáticamente los métodos getter, setter, toString, equals y hashCode.
 */
@Entity
@Table(name = "documentos")
@Data
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @NotBlank(message = "El nombre original del archivo no puede estar vacío")
    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "file_path")
    private String filePath;

    @NotBlank(message = "La URL del archivo no puede estar vacía")
    @Column(name = "file_url", nullable = false)
    private String fileUrl; // URL del objeto en S3

    @Column(name = "encrypted_content")
    private String encryptedContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * Obtiene la ruta del archivo.
     * @return la URL del archivo en S3
     */
    public String getFilePath() {
        return fileUrl;
    }

    /**
     * Establece la ruta del archivo.
     * @param filePath la nueva ruta del archivo
     */
    public void setFilePath(String filePath) {
        this.fileUrl = filePath;
    }

    /**
     * Establece el nombre del archivo.
     * @param fileName el nuevo nombre del archivo
     */
    public void setFileName(String fileName) {
        this.originalFileName = fileName;
    }

    /**
     * Obtiene el ID del usuario asociado al documento.
     * @return el ID del usuario
     */
    public Long getUsuarioId() {
        return usuario != null ? usuario.getId() : null;
    }

    /**
     * Establece el ID del usuario asociado al documento.
     * @param usuarioId el ID del usuario
     */
    public void setUsuarioId(Long usuarioId) {
        if (this.usuario == null) {
            this.usuario = new Usuario();
        }
        this.usuario.setId(usuarioId);
    }
}


