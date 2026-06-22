package com.aduanas.msauth.repository;

import com.aduanas.msauth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByRutOrEmail(String rut, String email);

    boolean existsByEmail(String email);

    boolean existsByRut(String rut);
}