// service/DuenoService.java
package com.huellitas.backend.service;

import com.huellitas.backend.dto.DuenoDTO;
import com.huellitas.backend.dto.DuenoRequestDTO;
import com.huellitas.backend.exception.ResourceNotFoundException;
import com.huellitas.backend.model.Dueno;
import com.huellitas.backend.repository.DuenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio con la lógica de negocio para Dueños.
 * Convierte entre entidades y DTOs para evitar
 * problemas de lazy loading en la serialización JSON.
 */
@Service
public class DuenoService {

    @Autowired
    private DuenoRepository duenoRepository;

    /**
     * Convierte una entidad Dueno a DuenoDTO.
     * Incluye el total de mascotas registradas.
     */
    private DuenoDTO convertToDTO(Dueno dueno) {
        DuenoDTO dto = new DuenoDTO();
        dto.setIdDueno(dueno.getIdDueno());
        dto.setNombre(dueno.getNombre());
        dto.setApellido(dueno.getApellido());
        dto.setDocumento(dueno.getDocumento());
        dto.setTelefono(dueno.getTelefono());
        dto.setEmail(dueno.getEmail());
        dto.setDireccion(dueno.getDireccion());
        // Cuenta las mascotas de forma segura (null-safe)
        dto.setTotalMascotas(dueno.getMascotas() != null ? dueno.getMascotas().size() : 0);
        return dto;
    }

    /** Obtiene todos los dueños como DTOs */
    public List<DuenoDTO> getAllDuenos() {
        return duenoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /** Obtiene un dueño por su ID como DTO */
    public DuenoDTO getDuenoById(Long id) {
        Dueno dueno = duenoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Dueño no encontrado con ID: " + id));
        return convertToDTO(dueno);
    }

    /** Crea un nuevo dueño a partir del DTO de request */
    public DuenoDTO createDueno(DuenoRequestDTO request) {
        Dueno dueno = new Dueno();
        dueno.setNombre(request.getNombre());
        dueno.setApellido(request.getApellido());
        dueno.setDocumento(request.getDocumento());
        dueno.setTelefono(request.getTelefono());
        dueno.setEmail(request.getEmail());
        dueno.setDireccion(request.getDireccion());
        Dueno guardado = duenoRepository.save(dueno);
        return convertToDTO(guardado);
    }

    /** Actualiza un dueño existente */
    public DuenoDTO updateDueno(Long id, DuenoRequestDTO request) {
        Dueno dueno = duenoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Dueño no encontrado con ID: " + id));

        dueno.setNombre(request.getNombre());
        dueno.setApellido(request.getApellido());
        dueno.setDocumento(request.getDocumento());
        dueno.setTelefono(request.getTelefono());
        dueno.setEmail(request.getEmail());
        dueno.setDireccion(request.getDireccion());

        Dueno actualizado = duenoRepository.save(dueno);
        return convertToDTO(actualizado);
    }

    /** Elimina un dueño por su ID */
    public void deleteDueno(Long id) {
        Dueno dueno = duenoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Dueño no encontrado con ID: " + id));
        duenoRepository.delete(dueno);
    }

    /** Busca dueños por término (nombre, apellido o documento) */
    public List<DuenoDTO> searchDuenos(String termino) {
        return duenoRepository
                .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrDocumentoContaining(
                        termino, termino, termino)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}