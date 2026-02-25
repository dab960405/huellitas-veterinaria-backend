// service/CitaService.java
package com.huellitas.backend.service;

import com.huellitas.backend.dto.CitaDTO;
import com.huellitas.backend.dto.CitaRequestDTO;
import com.huellitas.backend.exception.ResourceNotFoundException;
import com.huellitas.backend.model.Cita;
import com.huellitas.backend.model.Mascota;
import com.huellitas.backend.repository.CitaRepository;
import com.huellitas.backend.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio con la lógica de negocio para Citas.
 * Incluye validación de fechas pasadas solo en creación
 * o cuando la fecha cambia en actualización.
 */
@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    /**
     * Convierte una entidad Cita a CitaDTO.
     * Incluye nombre de mascota y nombre del dueño.
     */
    private CitaDTO convertToDTO(Cita cita) {
        CitaDTO dto = new CitaDTO();
        dto.setIdCita(cita.getIdCita());
        dto.setFecha(cita.getFecha());
        dto.setHora(cita.getHora());
        dto.setMotivo(cita.getMotivo());
        dto.setEstado(cita.getEstado().name());
        dto.setIdMascota(cita.getMascota().getIdMascota());
        dto.setNombreMascota(cita.getMascota().getNombre());
        dto.setNombreDueno(
                cita.getMascota().getDueno().getNombre() + " " +
                        cita.getMascota().getDueno().getApellido()
        );
        return dto;
    }

    /** Obtiene todas las citas como DTOs */
    public List<CitaDTO> getAllCitas() {
        return citaRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /** Obtiene una cita por ID */
    public CitaDTO getCitaById(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada con ID: " + id));
        return convertToDTO(cita);
    }

    /**
     * Crea una nueva cita.
     * VALIDACIÓN: No permite citas en fechas pasadas.
     */
    public CitaDTO createCita(CitaRequestDTO request) {
        // ===== VALIDACIÓN: No permitir fechas pasadas =====
        if (request.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("No se permiten citas en fechas pasadas");
        }

        Mascota mascota = mascotaRepository.findById(request.getIdMascota())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Mascota no encontrada con ID: " + request.getIdMascota()));

        Cita cita = new Cita();
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        cita.setMotivo(request.getMotivo());
        cita.setMascota(mascota);

        if (request.getEstado() != null && !request.getEstado().isEmpty()) {
            cita.setEstado(Cita.EstadoCita.valueOf(request.getEstado()));
        }

        Cita guardada = citaRepository.save(cita);
        return convertToDTO(guardada);
    }

    /**
     * Actualiza una cita existente.
     * VALIDACIÓN: Solo bloquea fechas pasadas si la fecha cambió.
     * Permite cambiar estado (ej: PROGRAMADA → COMPLETADA) aunque la fecha sea pasada.
     */
    public CitaDTO updateCita(Long id, CitaRequestDTO request) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada con ID: " + id));

        // ===== VALIDACIÓN: solo si la fecha realmente cambió =====
        boolean fechaCambio = !request.getFecha().equals(cita.getFecha());
        if (fechaCambio && request.getFecha().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("No se permiten citas en fechas pasadas");
        }

        Mascota mascota = mascotaRepository.findById(request.getIdMascota())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Mascota no encontrada con ID: " + request.getIdMascota()));

        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        cita.setMotivo(request.getMotivo());
        cita.setMascota(mascota);

        if (request.getEstado() != null && !request.getEstado().isEmpty()) {
            cita.setEstado(Cita.EstadoCita.valueOf(request.getEstado()));
        }

        Cita actualizada = citaRepository.save(cita);
        return convertToDTO(actualizada);
    }

    /** Elimina una cita por ID */
    public void deleteCita(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada con ID: " + id));
        citaRepository.delete(cita);
    }

    /** Obtiene citas de una mascota específica */
    public List<CitaDTO> getCitasByMascota(Long idMascota) {
        return citaRepository.findByMascotaIdMascota(idMascota)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}