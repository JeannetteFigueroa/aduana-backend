package com.aduanas.mspdi.repository;

import com.aduanas.mspdi.model.Validacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValidacionRepository extends JpaRepository<Validacion, Long> {
    List<Validacion> findByViajeroId(String viajeroId);
}