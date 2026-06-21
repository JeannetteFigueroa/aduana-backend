package com.aduanas.msusuarios.controller;

import com.aduanas.msusuarios.dto.*;

import com.aduanas.msusuarios.service.*;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class FuncionarioController {

        private final FuncionarioService service;

        @PostMapping
        public ResponseEntity<FuncionarioResponseDTO> crearFuncionario(
                        @Valid @RequestBody FuncionarioRequestDTO dto) {

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(service.crearFuncionario(dto));
        }

        @GetMapping
        public ResponseEntity<List<FuncionarioResponseDTO>> obtenerTodos() {

                return ResponseEntity.ok(
                                service.obtenerTodos());
        }

        @GetMapping("/{id}")
        public ResponseEntity<FuncionarioResponseDTO> obtenerPorId(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                service.obtenerPorId(id));
        }

        @GetMapping("/rut/{rut}")
        public ResponseEntity<FuncionarioResponseDTO> obtenerPorRut(
                        @PathVariable String rut) {

                return ResponseEntity.ok(
                                service.obtenerPorRut(rut));
        }

        @PutMapping("/{id}")
        public ResponseEntity<FuncionarioResponseDTO> actualizar(
                        @PathVariable Long id,
                        @RequestBody FuncionarioRequestDTO request) {

                return ResponseEntity.ok(
                                service.actualizarFuncionario(
                                                id,
                                                request));
        }

        @PutMapping("/{id}/rol")
        public ResponseEntity<FuncionarioResponseDTO> cambiarRol(
                        @PathVariable Long id,
                        @RequestBody CambioRolDTO request) {

                return ResponseEntity.ok(
                                service.cambiarRol(
                                                id,
                                                request));
        }

        @PutMapping("/{id}/activar")
        public ResponseEntity<String> activar(
                        @PathVariable Long id) {

                service.activarFuncionario(id);

                return ResponseEntity.ok(
                                "Funcionario activado");
        }

        @PutMapping("/{id}/desactivar")
        public ResponseEntity<String> desactivar(
                        @PathVariable Long id) {

                service.desactivarFuncionario(id);

                return ResponseEntity.ok(
                                "Funcionario desactivado");
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> eliminar(
                        @PathVariable Long id) {

                service.eliminarFuncionario(id);

                return ResponseEntity.ok(
                                "Funcionario eliminado");
        }
}