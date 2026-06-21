package com.aduanas.msviajeros.controller;

import com.aduanas.msviajeros.dto.CambioRiesgoDTO;
import com.aduanas.msviajeros.dto.ViajeroRequestDTO;
import com.aduanas.msviajeros.dto.ViajeroResponseDTO;
import com.aduanas.msviajeros.service.ViajeroService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viajeros")
@RequiredArgsConstructor
public class ViajeroController {

        private final ViajeroService service;

        @PostMapping
        public ResponseEntity<ViajeroResponseDTO> crear(
                        @Valid @RequestBody ViajeroRequestDTO dto) {

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(service.crearViajero(dto));
        }

        @GetMapping
        public ResponseEntity<List<ViajeroResponseDTO>> obtenerTodos() {

                return ResponseEntity.ok(
                                service.obtenerTodos());
        }

        @GetMapping("/{id}")
        public ResponseEntity<ViajeroResponseDTO> obtenerPorId(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                service.obtenerPorId(id));
        }

        @GetMapping("/rut/{rut}")
        public ResponseEntity<ViajeroResponseDTO> obtenerPorRut(
                        @PathVariable String rut) {

                return ResponseEntity.ok(
                                service.obtenerPorRut(rut));
        }

        @PutMapping("/{id}")
        public ResponseEntity<ViajeroResponseDTO> actualizar(
                        @PathVariable Long id,
                        @RequestBody ViajeroRequestDTO request) {

                return ResponseEntity.ok(
                                service.actualizarViajero(
                                                id,
                                                request));
        }

        @PutMapping("/{id}/riesgo")
        public ResponseEntity<ViajeroResponseDTO> cambiarRiesgo(
                        @PathVariable Long id,
                        @Valid @RequestBody CambioRiesgoDTO request) {

                return ResponseEntity.ok(
                                service.cambiarRiesgo(
                                                id,
                                                request.getRiesgo()));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> eliminar(@PathVariable Long id) {

                service.eliminarViajero(id);

                return ResponseEntity.ok(
                                "Viajero eliminado");
        }
}
