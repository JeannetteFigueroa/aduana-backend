package com.aduanas.msusuarios.service;

import java.util.List;

import com.aduanas.msusuarios.dto.CambioRolDTO;
import com.aduanas.msusuarios.dto.FuncionarioRequestDTO;
import com.aduanas.msusuarios.dto.FuncionarioResponseDTO;

public interface FuncionarioService {

    FuncionarioResponseDTO crearFuncionario(
            FuncionarioRequestDTO request);

    List<FuncionarioResponseDTO> obtenerTodos();

    FuncionarioResponseDTO obtenerPorId(Long id);

    FuncionarioResponseDTO obtenerPorRut(String rut);

    FuncionarioResponseDTO actualizarFuncionario(
            Long id,
            FuncionarioRequestDTO request);

    void desactivarFuncionario(Long id);

    FuncionarioResponseDTO cambiarRol(
            Long id,
            CambioRolDTO request);
}
