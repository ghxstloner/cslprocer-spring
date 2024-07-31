package com.cslprocerbackend.repository;

import com.cslprocerbackend.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
}
