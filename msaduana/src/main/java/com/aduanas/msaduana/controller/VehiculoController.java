package com.aduanas.msaduana.controller;

import com.aduanas.msaduana.dto.VehiculoRequestDTO;
import com.aduanas.msaduana.dto.VehiculoResponseDTO;
import com.aduanas.msaduana.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    @Autowired
    private VehiculoService service;

    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> crear(@RequestBody VehiculoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearVehiculo(request));
    }

    @GetMapping("/{id}")
    public VehiculoResponseDTO buscar(@PathVariable Long id) {
        return service.buscarVehiculo(id);
    }

    @GetMapping("/patente/{patente}")
    public VehiculoResponseDTO buscarPorPatente(@PathVariable String patente) {
        return service.buscarPorPatente(patente);
    }

    @GetMapping
    public List<VehiculoResponseDTO> listar() {
        return service.listarVehiculos();
    }

    @PostMapping("/{id}/autorizar")
    public VehiculoResponseDTO autorizar(@PathVariable Long id, @RequestParam boolean autorizar) {
        return service.autorizarPaso(id, autorizar);
    }
}