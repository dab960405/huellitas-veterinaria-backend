// repository/DuenoRepository.java
package com.huellitas.backend.repository;

import com.huellitas.backend.model.Dueno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para operaciones CRUD de Dueños.
 * Spring Data JPA genera las queries automáticamente.
 */
@Repository
public interface DuenoRepository extends JpaRepository<Dueno, Long> {

    /** Buscar dueños por nombre (contiene, ignora mayúsculas) */
    List<Dueno> findByNombreContainingIgnoreCase(String nombre);

    /** Buscar dueño por documento exacto */
    Dueno findByDocumento(String documento);

    /** Buscar dueños por nombre o apellido o documento */
    List<Dueno> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrDocumentoContaining(
            String nombre, String apellido, String documento);
}