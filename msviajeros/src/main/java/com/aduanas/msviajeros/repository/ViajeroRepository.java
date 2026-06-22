package com.aduanas.msviajeros.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.aduanas.msviajeros.model.Viajero;

public interface ViajeroRepository extends JpaRepository<Viajero, Long> {

    Optional<Viajero> findByRut(String rut);

    boolean existsByRut(String rut);

    List<Viajero> findByEstado(String estado);
}
