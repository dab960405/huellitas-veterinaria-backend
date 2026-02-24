// service/MascotaService.java
package com.huellitas.backend.service;

import com.huellitas.backend.dto.MascotaDTO;
import com.huellitas.backend.dto.MascotaRequestDTO;
import com.huellitas.backend.exception.ResourceNotFoundException;
import com.huellitas.backend.model.Dueno;
import com.huellitas.backend.model.Mascota;
import com.huellitas.backend.repository.DuenoRepository;
import com.huellitas.backend.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio con la lógica de negocio para Mascotas.
 * Convierte entre entidades y DTOs.
 */
@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private DuenoRepository duenoRepository;

    /**
     * Convierte una entidad Mascota a MascotaDTO.
     * Incluye datos del dueño y cálculo automático de edad.
     */
    private MascotaDTO convertToDTO(Mascota mascota) {
        MascotaDTO dto = new MascotaDTO();
        dto.setIdMascota(mascota.getIdMascota());
        dto.setNombre(mascota.getNombre());
        dto.setEspecie(mascota.getEspecie());
        dto.setRaza(mascota.getRaza());
        dto.setFechaNacimiento(mascota.getFechaNacimiento());
        dto.setEdad(mascota.getEdad()); // Cálculo automático
        dto.setIdDueno(mascota.getDueno().getIdDueno());
        dto.setNombreDueno(mascota.getDueno().getNombre() + " " + mascota.getDueno().getApellido());
        dto.setDocumentoDueno(mascota.getDueno().getDocumento());
        return dto;
    }

    /** Obtiene todas las mascotas como DTOs */
    public List<MascotaDTO> getAllMascotas() {
        return mascotaRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /** Obtiene una mascota por ID como DTO */
    public MascotaDTO getMascotaById(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Mascota no encontrada con ID: " + id));
        return convertToDTO(mascota);
    }

    /** Crea una nueva mascota a partir del DTO de request */
    public MascotaDTO createMascota(MascotaRequestDTO request) {
        // Verificar que el dueño existe
        Dueno dueno = duenoRepository.findById(request.getIdDueno())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Dueño no encontrado con ID: " + request.getIdDueno()));

        Mascota mascota = new Mascota();
        mascota.setNombre(request.getNombre());
        mascota.setEspecie(request.getEspecie());
        mascota.setRaza(request.getRaza());
        mascota.setFechaNacimiento(request.getFechaNacimiento());
        mascota.setDueno(dueno);

        Mascota guardada = mascotaRepository.save(mascota);
        return convertToDTO(guardada);
    }

    /** Actualiza una mascota existente */
    public MascotaDTO updateMascota(Long id, MascotaRequestDTO request) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Mascota no encontrada con ID: " + id));

        Dueno dueno = duenoRepository.findById(request.getIdDueno())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Dueño no encontrado con ID: " + request.getIdDueno()));

        mascota.setNombre(request.getNombre());
        mascota.setEspecie(request.getEspecie());
        mascota.setRaza(request.getRaza());
        mascota.setFechaNacimiento(request.getFechaNacimiento());
        mascota.setDueno(dueno);

        Mascota actualizada = mascotaRepository.save(mascota);
        return convertToDTO(actualizada);
    }

    /** Elimina una mascota por ID */
    public void deleteMascota(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Mascota no encontrada con ID: " + id));
        mascotaRepository.delete(mascota);
    }

    /**
     * Busca mascotas por nombre de mascota, nombre de dueño o documento.
     * Requisito: filtrar mascotas por nombre o documento del dueño.
     */
    public List<MascotaDTO> searchMascotas(String termino) {
        return mascotaRepository.buscarPorNombreODocumentoDueno(termino)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /** Obtiene todas las mascotas de un dueño específico */
    public List<MascotaDTO> getMascotasByDueno(Long idDueno) {
        return mascotaRepository.findByDuenoIdDueno(idDueno)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}