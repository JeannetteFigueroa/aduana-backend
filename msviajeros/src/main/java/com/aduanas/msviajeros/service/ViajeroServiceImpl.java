package com.aduanas.msviajeros.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aduanas.msviajeros.dto.MenorRequestDTO;
import com.aduanas.msviajeros.dto.MenorResponseDTO;
import com.aduanas.msviajeros.dto.ViajeroRequestDTO;
import com.aduanas.msviajeros.dto.ViajeroResponseDTO;
import com.aduanas.msviajeros.exception.RutDuplicadoException;
import com.aduanas.msviajeros.exception.ViajeroNoEncontradoException;
import com.aduanas.msviajeros.model.Menor;
import com.aduanas.msviajeros.model.Viajero;
import com.aduanas.msviajeros.dto.ViajeroCompletoDTO;
import com.aduanas.msviajeros.repository.MenorRepository;
import com.aduanas.msviajeros.repository.ViajeroRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ViajeroServiceImpl implements ViajeroService {

        private final ViajeroRepository repository;
        private final MenorRepository menorRepository;

        @Override
        public ViajeroResponseDTO crearViajero(
                        ViajeroRequestDTO request) {

                if (repository.existsByRut(
                                request.getRut())) {

                        throw new RutDuplicadoException(
                                        "Rut ya registrado");
                }

                Viajero viajero = Viajero.builder()
                                .rut(request.getRut())
                                .nombres(request.getNombres())
                                .apellidos(request.getApellidos())
                                .documento(request.getDocumento())
                                .nacionalidad(request.getNacionalidad())
                                .vehiculo(request.getVehiculo())
                                .patente(request.getPatente())
                                .origen(request.getOrigen())
                                .destino(request.getDestino())
                                .estado(request.getEstado() != null ? request.getEstado() : "ACTIVO")
                                .riesgo(request.getRiesgo() != null ? request.getRiesgo() : "BAJO")
                                .fechaIngreso(
                                                LocalDateTime.now())
                                .build();

                repository.save(viajero);

                return mapear(viajero);
        }

        @Override
        public List<ViajeroResponseDTO> obtenerTodos() {
                return repository.findAll()
                                .stream()
                                .map(this::mapear)
                                .toList();
        }

        @Override
        public List<ViajeroResponseDTO> obtenerPorEstado(String estado) {
                return repository.findByEstado(estado)
                                .stream()
                                .map(this::mapear)
                                .toList();
        }

        @Override
        public ViajeroResponseDTO obtenerPorId(
                        Long id) {

                Viajero viajero = repository.findById(id)
                                .orElseThrow(() -> new ViajeroNoEncontradoException(
                                                "Viajero no encontrado"));

                return mapear(viajero);
        }

        @Override
        public ViajeroCompletoDTO obtenerCompleto(Long id) {
                Viajero viajero = repository.findById(id)
                                .orElseThrow(() -> new ViajeroNoEncontradoException("Viajero no encontrado"));

                ViajeroCompletoDTO dto = new ViajeroCompletoDTO();
                dto.setId(viajero.getId());
                dto.setNombre(viajero.getNombres() + " " + viajero.getApellidos());
                dto.setRut(viajero.getRut());

                return dto;
        }

        @Override
        public ViajeroResponseDTO obtenerPorRut(
                        String rut) {

                Viajero viajero = repository.findByRut(rut)
                                .orElseThrow(() -> new ViajeroNoEncontradoException(
                                                "Viajero no encontrado"));

                return mapear(viajero);
        }

        @Override
        public List<MenorResponseDTO> obtenerMenores(Long viajeroId) {
                Viajero viajero = repository.findById(viajeroId)
                                .orElseThrow(() -> new ViajeroNoEncontradoException("Viajero no encontrado"));
                return viajero.getMenores().stream()
                                .map(this::mapMenor)
                                .toList();
        }

        @Override
        public MenorResponseDTO agregarMenor(Long viajeroId, MenorRequestDTO request) {
                Viajero viajero = repository.findById(viajeroId)
                                .orElseThrow(() -> new ViajeroNoEncontradoException("Viajero no encontrado"));

                Menor menor = new Menor(
                                request.getNombre(),
                                request.getRut(),
                                request.getFechaNacimiento(),
                                request.getParentesco(),
                                viajero);

                menorRepository.save(menor);
                return mapMenor(menor);
        }

        private MenorResponseDTO mapMenor(Menor m) {
                return new MenorResponseDTO(
                                m.getId(),
                                m.getNombre(),
                                m.getRut(),
                                m.getFechaNacimiento(),
                                m.getParentesco(),
                                m.isAutorizado());
        }

        @Override
        public ViajeroResponseDTO actualizarViajero(
                        Long id,
                        ViajeroRequestDTO request) {

                Viajero viajero = repository.findById(id)
                                .orElseThrow(() -> new ViajeroNoEncontradoException(
                                                "Viajero no encontrado"));

                viajero.setNombres(
                                request.getNombres());

                viajero.setApellidos(
                                request.getApellidos());

                viajero.setDocumento(
                                request.getDocumento());

                viajero.setNacionalidad(
                                request.getNacionalidad());

                viajero.setVehiculo(
                                request.getVehiculo());

                viajero.setPatente(
                                request.getPatente());

                viajero.setOrigen(
                                request.getOrigen());

                viajero.setDestino(
                                request.getDestino());

                viajero.setEstado(
                                request.getEstado() != null ? request.getEstado() : viajero.getEstado());

                viajero.setRiesgo(
                                request.getRiesgo() != null ? request.getRiesgo() : viajero.getRiesgo());

                repository.save(viajero);

                return mapear(viajero);
        }

        @Override
        public void eliminarViajero(
                        Long id) {

                Viajero viajero = repository.findById(id)
                                .orElseThrow(() -> new ViajeroNoEncontradoException(
                                                "Viajero no encontrado"));

                repository.delete(viajero);
        }

        @Override
        public ViajeroResponseDTO cambiarRiesgo(
                        Long id,
                        String riesgo) {

                Viajero viajero = repository.findById(id)
                                .orElseThrow(() -> new ViajeroNoEncontradoException(
                                                "Viajero no encontrado"));

                viajero.setRiesgo(riesgo);

                repository.save(viajero);

                return mapear(viajero);
        }

        private ViajeroResponseDTO mapear(
                        Viajero viajero) {

                return ViajeroResponseDTO.builder()
                                .id(viajero.getId())
                                .rut(viajero.getRut())
                                .nombres(viajero.getNombres())
                                .apellidos(viajero.getApellidos())
                                .documento(viajero.getDocumento())
                                .nacionalidad(viajero.getNacionalidad())
                                .vehiculo(viajero.getVehiculo())
                                .patente(viajero.getPatente())
                                .origen(viajero.getOrigen())
                                .destino(viajero.getDestino())
                                .estado(viajero.getEstado())
                                .riesgo(viajero.getRiesgo())
                                .fechaIngreso(
                                                viajero.getFechaIngreso() != null
                                                                ? viajero.getFechaIngreso().toString()
                                                                : null)
                                .build();
        }
}
