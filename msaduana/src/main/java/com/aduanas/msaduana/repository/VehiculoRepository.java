package com.aduanas.msaduana.repository;

import com.aduanas.msaduana.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    Optional<Vehiculo> findByPatente(String patente);
    Optional<Vehiculo> findByRutDuenio(String rutDuenio);
    boolean existsByPatente(String patente);
}