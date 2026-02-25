// controller/DuenoController.java
package com.huellitas.backend.controller;

import com.huellitas.backend.dto.DuenoDTO;
import com.huellitas.backend.dto.DuenoRequestDTO;
import com.huellitas.backend.service.DuenoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador REST para gestionar Dueños.
 * Base path: /api/duenos
 */
@RestController
@RequestMapping("/api/duenos")
public class DuenoController {

    @Autowired
    private DuenoService duenoService;

    /**
     * Obtiene todos los dueños registrados.
     * GET /api/duenos
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllDuenos() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", duenoService.getAllDuenos());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un dueño por su ID.
     * GET /api/duenos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getDuenoById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", duenoService.getDuenoById(id));
        return ResponseEntity.ok(response);
    }

    /**
     * Crea un nuevo dueño.
     * POST /api/duenos
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createDueno(
            @Valid @RequestBody DuenoRequestDTO request) {
        Map<String, Object> response = new HashMap<>();
        DuenoDTO nuevoDueno = duenoService.createDueno(request);
        response.put("success", true);
        response.put("message", "Dueño creado exitosamente");
        response.put("data", nuevoDueno);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Actualiza un dueño existente.
     * PUT /api/duenos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateDueno(
            @PathVariable Long id,
            @Valid @RequestBody DuenoRequestDTO request) {
        Map<String, Object> response = new HashMap<>();
        DuenoDTO duenoActualizado = duenoService.updateDueno(id, request);
        response.put("success", true);
        response.put("message", "Dueño actualizado exitosamente");
        response.put("data", duenoActualizado);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un dueño por su ID.
     * DELETE /api/duenos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteDueno(@PathVariable Long id) {
        duenoService.deleteDueno(id);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Dueño eliminado exitosamente");
        return ResponseEntity.ok(response);
    }

    /**
     * Busca dueños por nombre, apellido o documento.
     * GET /api/duenos/buscar?termino=xxx
     */
    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> searchDuenos(
            @RequestParam String termino) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", duenoService.searchDuenos(termino));
        return ResponseEntity.ok(response);
    }
}