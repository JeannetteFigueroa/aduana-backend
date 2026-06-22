package com.aduanas.msaduana.service;

import com.aduanas.msaduana.dto.VehiculoRequestDTO;
import com.aduanas.msaduana.dto.VehiculoResponseDTO;
import com.aduanas.msaduana.model.Vehiculo;

import java.util.List;

public interface VehiculoService {
    VehiculoResponseDTO crearVehiculo(VehiculoRequestDTO request);
    VehiculoResponseDTO buscarVehiculo(Long id);
    VehiculoResponseDTO buscarPorPatente(String patente);
    List<VehiculoResponseDTO> listarVehiculos();
    VehiculoResponseDTO autorizarPaso(Long id, boolean autorizar);
}