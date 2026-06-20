package com.aduanas.msusuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aduanas.msusuarios.model.Funcionario;

public interface FuncionarioRepository
        extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByRut(String rut);

    Optional<Funcionario> findByCorreoInstitucional(
            String correoInstitucional);

    boolean existsByRut(String rut);

    boolean existsByCorreoInstitucional(
            String correoInstitucional);
}
