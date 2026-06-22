package com.aduanas.msauth.repository;

import com.aduanas.msauth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.rut = :identificador OR u.email = :identificador")
    Optional<Usuario> findByIdentificador(@Param("identificador") String identificador);

    boolean existsByEmail(String email);

    boolean existsByRut(String rut);
}