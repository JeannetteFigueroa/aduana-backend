package com.aduanas.msaduana.service;

import com.aduanas.msaduana.dto.VehiculoRequestDTO;
import com.aduanas.msaduana.dto.VehiculoResponseDTO;
import com.aduanas.msaduana.model.Vehiculo;
import com.aduanas.msaduana.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehiculoServiceImpl implements VehiculoService {
    @Autowired
    private VehiculoRepository repository;

    @Override
    public VehiculoResponseDTO crearVehiculo(VehiculoRequestDTO request) {
        if (repository.existsByPatente(request.getPatente())) {
            throw new RuntimeException("Patente ya registrada");
        }
        Vehiculo v = new Vehiculo();
        v.setPatente(request.getPatente());
        v.setMarca(request.getMarca());
        v.setModelo(request.getModelo());
        v.setColor(request.getColor());
        v.setAnio(request.getAnio());
        v.setRutDuenio(request.getRutDuenio());
        v.setNombreDuenio(request.getNombreDuenio());
        repository.save(v);
        return VehiculoResponseDTO.from(v);
    }

    @Override
    public VehiculoResponseDTO buscarVehiculo(Long id) {
        Vehiculo v = repository.findById(id).orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        return VehiculoResponseDTO.from(v);
    }

    @Override
    public VehiculoResponseDTO buscarPorPatente(String patente) {
        Vehiculo v = repository.findByPatente(patente).orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        return VehiculoResponseDTO.from(v);
    }

    @Override
    public List<VehiculoResponseDTO> listarVehiculos() {
        return repository.findAll().stream().map(VehiculoResponseDTO::from).collect(Collectors.toList());
    }

    @Override
    public VehiculoResponseDTO autorizarPaso(Long id, boolean autorizar) {
        Vehiculo v = repository.findById(id).orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        v.setEstado(autorizar ? Vehiculo.Estado.AUTORIZADO : Vehiculo.Estado.DENEGADO);
        repository.save(v);
        return VehiculoResponseDTO.from(v);
    }
}