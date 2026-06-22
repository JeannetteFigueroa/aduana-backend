package com.aduanas.msviajeros.service;

import java.util.List;
import com.aduanas.msviajeros.dto.CambioRiesgoDTO;
import com.aduanas.msviajeros.dto.MenorRequestDTO;
import com.aduanas.msviajeros.dto.MenorResponseDTO;
import com.aduanas.msviajeros.dto.ViajeroCompletoDTO;
import com.aduanas.msviajeros.dto.ViajeroRequestDTO;
import com.aduanas.msviajeros.dto.ViajeroResponseDTO;

public interface ViajeroService {

        ViajeroResponseDTO crearViajero(ViajeroRequestDTO request);

        List<ViajeroResponseDTO> obtenerTodos();

        List<ViajeroResponseDTO> obtenerPorEstado(String estado);

        ViajeroResponseDTO obtenerPorId(Long id);

        ViajeroCompletoDTO obtenerCompleto(Long id);

        ViajeroResponseDTO obtenerPorRut(String rut);

        List<MenorResponseDTO> obtenerMenores(Long viajeroId);

        MenorResponseDTO agregarMenor(Long viajeroId, MenorRequestDTO request);

        ViajeroResponseDTO actualizarViajero(Long id, ViajeroRequestDTO request);

        void eliminarViajero(Long id);

        ViajeroResponseDTO cambiarRiesgo(Long id, String riesgo);
}
