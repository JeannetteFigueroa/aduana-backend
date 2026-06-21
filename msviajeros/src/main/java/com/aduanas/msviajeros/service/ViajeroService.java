package com.aduanas.msviajeros.service;

import java.util.List;
import com.aduanas.msviajeros.dto.CambioRiesgoDTO;
import com.aduanas.msviajeros.dto.ViajeroRequestDTO;
import com.aduanas.msviajeros.dto.ViajeroResponseDTO;

public interface ViajeroService {

    ViajeroResponseDTO crearViajero(ViajeroRequestDTO request);

    List<ViajeroResponseDTO> obtenerTodos();

    ViajeroResponseDTO obtenerPorId(Long id);

    ViajeroResponseDTO obtenerPorRut(String rut);

    ViajeroResponseDTO actualizarViajero(Long id, ViajeroRequestDTO request);

    void eliminarViajero(Long id);

    ViajeroResponseDTO cambiarRiesgo(Long id, String riesgo);
}
