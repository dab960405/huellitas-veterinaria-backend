// service/DuenoService.java
package com.huellitas.backend.service;

import com.huellitas.backend.exception.ResourceNotFoundException;
import com.huellitas.backend.model.Dueno;
import com.huellitas.backend.repository.DuenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio que contiene la lógica de negocio para Dueños.
 */
@Service
public class DuenoService {

    @Autowired
    private DuenoRepository duenoRepository;

    /** Obtiene todos los dueños */
    public List<Dueno> getAllDuenos() {
        return duenoRepository.findAll();
    }

    /** Obtiene un dueño por su ID */
    public Dueno getDuenoById(Long id) {
        return duenoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Dueño no encontrado con ID: " + id));
    }

    /** Crea un nuevo dueño */
    public Dueno createDueno(Dueno dueno) {
        return duenoRepository.save(dueno);
    }

    /** Actualiza un dueño existente */
    public Dueno updateDueno(Long id, Dueno duenoDetails) {
        Dueno dueno = getDuenoById(id);

        dueno.setNombre(duenoDetails.getNombre());
        dueno.setApellido(duenoDetails.getApellido());
        dueno.setDocumento(duenoDetails.getDocumento());
        dueno.setTelefono(duenoDetails.getTelefono());
        dueno.setEmail(duenoDetails.getEmail());
        dueno.setDireccion(duenoDetails.getDireccion());

        return duenoRepository.save(dueno);
    }

    /** Elimina un dueño por su ID */
    public void deleteDueno(Long id) {
        Dueno dueno = getDuenoById(id);
        duenoRepository.delete(dueno);
    }

    /** Busca dueños por término (nombre, apellido o documento) */
    public List<Dueno> searchDuenos(String termino) {
        return duenoRepository
                .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCaseOrDocumentoContaining(
                        termino, termino, termino);
    }
}