package com.aduanas.msusuarios.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aduanas.msusuarios.dto.CambioRolDTO;
import com.aduanas.msusuarios.dto.FuncionarioRequestDTO;
import com.aduanas.msusuarios.dto.FuncionarioResponseDTO;
import com.aduanas.msusuarios.exception.CorreoDuplicadoException;
import com.aduanas.msusuarios.exception.CorreoInstitucionalException;
import com.aduanas.msusuarios.exception.FuncionarioNoEncontradoException;
import com.aduanas.msusuarios.exception.RolInvalidoException;
import com.aduanas.msusuarios.exception.RutDuplicadoException;
import com.aduanas.msusuarios.model.Funcionario;
import com.aduanas.msusuarios.repository.FuncionarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FuncionarioServiceImpl implements FuncionarioService {

        private final FuncionarioRepository repository;

        private final List<String> ROLES = List.of(
                        "ADMIN",
                        "SUPERVISOR",
                        "FISCALIZADOR",
                        "OPERADOR",
                        "FUNCIONARIO");

        // Método para crear funcionario
        @Override
        public FuncionarioResponseDTO crearFuncionario(
                        FuncionarioRequestDTO request) {

                if (repository.existsByRut(
                                request.getRut())) {

                        throw new RutDuplicadoException(
                                        "Rut ya registrado");
                }

                if (repository.existsByCorreoInstitucional(
                                request.getCorreoInstitucional())) {

                        throw new CorreoDuplicadoException(
                                        "Correo ya registrado");
                }

                if (!request.getCorreoInstitucional()
                                .endsWith("@aduana.cl")) {

                        throw new CorreoInstitucionalException(
                                        "Debe usar correo institucional");
                }

                if (!ROLES.contains(
                                request.getRol())) {

                        throw new RolInvalidoException(
                                        "Rol inválido");
                }

                Funcionario funcionario = Funcionario.builder()
                                .rut(request.getRut())
                                .nombres(request.getNombres())
                                .apellidos(request.getApellidos())
                                .correoInstitucional(
                                                request.getCorreoInstitucional())
                                .rol(request.getRol())
                                .estado("ACTIVO")
                                .fechaCreacion(
                                                LocalDateTime.now())
                                .build();

                repository.save(funcionario);

                return mapear(funcionario);
        }

        // Método para buscar por id
        @Override
        public FuncionarioResponseDTO obtenerPorId(
                        Long id) {

                Funcionario funcionario = repository.findById(id)
                                .orElseThrow(() -> new FuncionarioNoEncontradoException(
                                                "Funcionario no encontrado"));

                return mapear(funcionario);
        }

        // Metodo para desactivar estado
        @Override
        public void desactivarFuncionario(
                        Long id) {

                Funcionario funcionario = repository.findById(id)
                                .orElseThrow(() -> new FuncionarioNoEncontradoException(
                                                "Funcionario no encontrado"));

                funcionario.setEstado(
                                "INACTIVO");

                repository.save(funcionario);
        }

        @Override
        public void activarFuncionario(Long id) {

                Funcionario funcionario = repository.findById(id)
                                .orElseThrow(() -> new FuncionarioNoEncontradoException(
                                                "Funcionario no encontrado"));

                funcionario.setEstado(
                                "ACTIVO");

                repository.save(funcionario);
        }

        @Override
        public void eliminarFuncionario(Long id) {

                Funcionario funcionario = repository.findById(id)
                                .orElseThrow(() -> new FuncionarioNoEncontradoException(
                                                "Funcionario no encontrado"));

                repository.delete(funcionario);
        }

        private FuncionarioResponseDTO mapear(
                        Funcionario funcionario) {

                return FuncionarioResponseDTO.builder()
                                .id(funcionario.getId())
                                .rut(funcionario.getRut())
                                .nombres(funcionario.getNombres())
                                .apellidos(funcionario.getApellidos())
                                .correoInstitucional(
                                                funcionario.getCorreoInstitucional())
                                .rol(funcionario.getRol())
                                .estado(funcionario.getEstado())
                                .build();
        }

        @Override
        public List<FuncionarioResponseDTO> obtenerTodos() {

                return repository.findAll()
                                .stream()
                                .map(this::mapear)
                                .toList();
        }

        @Override
        public FuncionarioResponseDTO obtenerPorRut(
                        String rut) {

                Funcionario funcionario = repository.findByRut(rut)
                                .orElseThrow(() -> new RuntimeException(
                                                "Funcionario no encontrado"));

                return mapear(funcionario);
        }

        @Override
        public FuncionarioResponseDTO actualizarFuncionario(
                        Long id,
                        FuncionarioRequestDTO request) {

                Funcionario funcionario = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Funcionario no encontrado"));

                funcionario.setNombres(
                                request.getNombres());

                funcionario.setApellidos(
                                request.getApellidos());

                funcionario.setCorreoInstitucional(
                                request.getCorreoInstitucional());

                funcionario.setFechaActualizacion(
                                LocalDateTime.now());

                repository.save(funcionario);

                return mapear(funcionario);
        }

        @Override
        public FuncionarioResponseDTO cambiarRol(
                        Long id,
                        CambioRolDTO request) {

                Funcionario funcionario = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Funcionario no encontrado"));

                if (!ROLES.contains(
                                request.getRol())) {

                        throw new RuntimeException(
                                        "Rol inválido");
                }

                funcionario.setRol(
                                request.getRol());

                repository.save(funcionario);

                return mapear(funcionario);
        }
}
