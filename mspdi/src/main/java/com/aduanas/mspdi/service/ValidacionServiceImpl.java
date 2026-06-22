package com.aduanas.mspdi.service;

import com.aduanas.mspdi.client.VehiculoClient;
import com.aduanas.mspdi.dto.ValidacionResponseDTO;
import com.aduanas.mspdi.exception.ValidacionNoEncontradaException;
import com.aduanas.mspdi.model.Validacion;
import com.aduanas.mspdi.repository.ValidacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidacionServiceImpl implements ValidacionService {

    @Autowired private ValidacionRepository repo;
    @Autowired private VehiculoClient vehiculoClient;

    @Override
    public ValidacionResponseDTO crear(String viajeroId, String entidad, String descripcion) {
        Validacion v = new Validacion(viajeroId, entidad, descripcion);
        return toDTO(repo.save(v));
    }

    @Override
    public List<ValidacionResponseDTO> listarPorViajero(String viajeroId) {
        return repo.findByViajeroId(viajeroId).stream().map(this::toDTO).toList();
    }

    @Override
    public ValidacionResponseDTO actualizar(Long id, String estado) {
        Validacion v = repo.findById(id).orElseThrow(() -> new ValidacionNoEncontradaException(id));
        v.setEstado(Validacion.Estado.valueOf(estado.toUpperCase()));
        return toDTO(repo.save(v));
    }

    private ValidacionResponseDTO toDTO(Validacion v) {
        return new ValidacionResponseDTO(v.getId(), v.getViajeroId(), v.getEntidad(), v.getDescripcion(), v.getEstado().toString().toLowerCase());
    }
}