// controller/CitaController.java
package com.huellitas.backend.controller;

import com.huellitas.backend.dto.CitaDTO;
import com.huellitas.backend.dto.CitaRequestDTO;
import com.huellitas.backend.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador REST para gestionar Citas veterinarias.
 * Base path: /api/citas
 */
@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    /**
     * Obtiene todas las citas.
     * GET /api/citas
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCitas() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", citaService.getAllCitas());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene una cita por ID.
     * GET /api/citas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getCitaById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", citaService.getCitaById(id));
        return ResponseEntity.ok(response);
    }

    /**
     * Crea una nueva cita con validación de fecha.
     * POST /api/citas
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createCita(
            @Valid @RequestBody CitaRequestDTO request) {
        Map<String, Object> response = new HashMap<>();
        try {
            CitaDTO nuevaCita = citaService.createCita(request);
            response.put("success", true);
            response.put("message", "Cita creada exitosamente");
            response.put("data", nuevaCita);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza una cita existente.
     * PUT /api/citas/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateCita(
            @PathVariable Long id,
            @Valid @RequestBody CitaRequestDTO request) {
        Map<String, Object> response = new HashMap<>();
        try {
            CitaDTO citaActualizada = citaService.updateCita(id, request);
            response.put("success", true);
            response.put("message", "Cita actualizada exitosamente");
            response.put("data", citaActualizada);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina una cita por ID.
     * DELETE /api/citas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCita(@PathVariable Long id) {
        citaService.deleteCita(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Cita eliminada exitosamente");
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene citas por mascota.
     * GET /api/citas/mascota/{idMascota}
     */
    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<Map<String, Object>> getCitasByMascota(
            @PathVariable Long idMascota) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", citaService.getCitasByMascota(idMascota));
        return ResponseEntity.ok(response);
    }
}