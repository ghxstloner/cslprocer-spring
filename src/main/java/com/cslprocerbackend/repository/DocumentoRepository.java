package com.cslprocerbackend.repository;

import com.cslprocerbackend.model.Documento;
import com.cslprocerbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Documento.
 * Esta interfaz proporciona operaciones CRUD básicas y métodos de búsqueda personalizados
 * para la entidad Documento.
 */
@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    /**
     * Busca todos los documentos asociados a un usuario específico.
     *
     * @param usuario El objeto Usuario asociado a los documentos.
     * @return Una lista de Documentos pertenecientes al usuario especificado.
     */
    List<Documento> findByUsuario(Usuario usuario);

    /**
     * Busca todos los documentos asociados a un usuario específico usando su ID.
     * @param usuarioId El ID del usuario asociado a los documentos.
     * @return Una lista de Documentos pertenecientes al usuario con el ID especificado.
     */
    List<Documento> findByUsuarioId(Long usuarioId);
}
