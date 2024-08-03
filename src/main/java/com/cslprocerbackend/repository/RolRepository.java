package com.cslprocerbackend.repository;

import com.cslprocerbackend.model.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<UsuarioRol, Long> {
    Optional<UsuarioRol> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}