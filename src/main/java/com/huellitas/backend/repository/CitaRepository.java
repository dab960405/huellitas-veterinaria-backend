// repository/CitaRepository.java
package com.huellitas.backend.repository;

import com.huellitas.backend.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para operaciones CRUD de Citas.
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    /** Obtener citas por mascota */
    List<Cita> findByMascotaIdMascota(Long idMascota);

    /** Obtener citas por estado */
    List<Cita> findByEstado(Cita.EstadoCita estado);
}