// repository/MascotaRepository.java
package com.huellitas.backend.repository;

import com.huellitas.backend.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para operaciones CRUD de Mascotas.
 * Incluye búsquedas personalizadas por nombre y documento del dueño.
 */
@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    /** Buscar mascotas por nombre (contiene, ignora mayúsculas) */
    List<Mascota> findByNombreContainingIgnoreCase(String nombre);

    /** Buscar mascotas por ID del dueño */
    List<Mascota> findByDuenoIdDueno(Long idDueno);

    /**
     * Busca mascotas por nombre de la mascota O por documento del dueño.
     * Usa JPQL para hacer join con la tabla de dueños.
     */
    @Query("SELECT m FROM Mascota m JOIN m.dueno d WHERE " +
            "LOWER(m.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
            "d.documento LIKE CONCAT('%', :termino, '%') OR " +
            "LOWER(d.nombre) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Mascota> buscarPorNombreODocumentoDueno(@Param("termino") String termino);
}