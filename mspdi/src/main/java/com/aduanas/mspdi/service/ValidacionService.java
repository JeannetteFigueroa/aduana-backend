package com.aduanas.mspdi.service;

import com.aduanas.mspdi.dto.ValidacionResponseDTO;
import java.util.List;

public interface ValidacionService {
    ValidacionResponseDTO crear(String viajeroId, String entidad, String descripcion);
    List<ValidacionResponseDTO> listarPorViajero(String viajeroId);
    ValidacionResponseDTO actualizar(Long id, String estado);
}