package com.aduanas.msviajeros.controller;

import com.aduanas.msviajeros.model.Documento;
import com.aduanas.msviajeros.repository.DocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/documentos")
@RequiredArgsConstructor
public class DocumentoController {

    private final DocumentoRepository repository;

    @GetMapping("/{viajeroId}")
    public ResponseEntity<List<Documento>> obtenerDocumentos(@PathVariable String viajeroId) {
        return ResponseEntity.ok(repository.findByViajeroId(viajeroId));
    }

    @PostMapping
    public ResponseEntity<Documento> crearDocumento(@RequestBody Documento dto) {
        dto.setEstado("subido");
        dto.setFechaSubida(new Date().toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documento> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado,
            @RequestParam(required = false) String observacion) {
        Documento doc = repository.findById(id).orElseThrow();
        doc.setEstado(estado);
        doc.setObservacion(observacion);
        return ResponseEntity.ok(repository.save(doc));
    }
}