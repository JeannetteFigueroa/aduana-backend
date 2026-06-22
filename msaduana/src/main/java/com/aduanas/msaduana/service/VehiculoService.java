package com.aduanas.msaduana.service;

import com.aduanas.msaduana.dto.VehiculoRequestDTO;
import com.aduanas.msaduana.dto.VehiculoResponseDTO;

import java.util.List;

public interface VehiculoService {
    VehiculoResponseDTO crearVehiculo(VehiculoRequestDTO request);
    VehiculoResponseDTO buscarVehiculo(Long id);
    VehiculoResponseDTO buscarPorPatente(String patente);
    VehiculoResponseDTO buscarPorRut(String rutDuenio);
    List<VehiculoResponseDTO> listarVehiculos();
    VehiculoResponseDTO autorizarPaso(Long id, boolean autorizar);
}