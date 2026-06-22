package com.aduanas.mspdi.controller;

import com.aduanas.mspdi.dto.ValidacionResponseDTO;
import com.aduanas.mspdi.service.ValidacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/validaciones")
public class ValidacionController {

    @Autowired private ValidacionService service;

    @GetMapping("/{viajeroId}")
    public List<ValidacionResponseDTO> listar(@PathVariable String viajeroId) {
        return service.listarPorViajero(viajeroId);
    }

    @PostMapping("/{viajeroId}/{entidad}")
    public ResponseEntity<ValidacionResponseDTO> crear(
            @PathVariable String viajeroId,
            @PathVariable String entidad,
            @RequestParam String descripcion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(viajeroId, entidad, descripcion));
    }

    @PutMapping("/{id}")
    public ValidacionResponseDTO actualizar(@PathVariable Long id, @RequestParam String estado) {
        return service.actualizar(id, estado);
    }
}