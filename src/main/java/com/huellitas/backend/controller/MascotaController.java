// controller/MascotaController.java
package com.huellitas.backend.controller;

import com.huellitas.backend.dto.MascotaDTO;
import com.huellitas.backend.dto.MascotaRequestDTO;
import com.huellitas.backend.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar Mascotas/Pacientes.
 * Base path: /api/mascotas
 */
@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    /**
     * Obtiene todas las mascotas.
     * GET /api/mascotas
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllMascotas() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", mascotaService.getAllMascotas());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene una mascota por ID.
     * GET /api/mascotas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getMascotaById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", mascotaService.getMascotaById(id));
        return ResponseEntity.ok(response);
    }

    /**
     * Crea una nueva mascota.
     * POST /api/mascotas
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createMascota(
            @Valid @RequestBody MascotaRequestDTO request) {
        Map<String, Object> response = new HashMap<>();
        MascotaDTO nuevaMascota = mascotaService.createMascota(request);
        response.put("success", true);
        response.put("message", "Mascota creada exitosamente");
        response.put("data", nuevaMascota);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza una mascota existente.
     * PUT /api/mascotas/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateMascota(
            @PathVariable Long id,
            @Valid @RequestBody MascotaRequestDTO request) {
        Map<String, Object> response = new HashMap<>();
        MascotaDTO mascotaActualizada = mascotaService.updateMascota(id, request);
        response.put("success", true);
        response.put("message", "Mascota actualizada exitosamente");
        response.put("data", mascotaActualizada);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina una mascota por ID.
     * DELETE /api/mascotas/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteMascota(@PathVariable Long id) {
        mascotaService.deleteMascota(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Mascota eliminada exitosamente");
        return ResponseEntity.ok(response);
    }

    /**
     * Busca mascotas por nombre o documento del dueño.
     * GET /api/mascotas/buscar?termino=xxx
     */
    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> searchMascotas(
            @RequestParam String termino) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", mascotaService.searchMascotas(termino));
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene mascotas por dueño.
     * GET /api/mascotas/dueno/{idDueno}
     */
    @GetMapping("/dueno/{idDueno}")
    public ResponseEntity<Map<String, Object>> getMascotasByDueno(
            @PathVariable Long idDueno) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", mascotaService.getMascotasByDueno(idDueno));
        return ResponseEntity.ok(response);
    }
}