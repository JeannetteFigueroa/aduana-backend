package com.aduanas.msviajeros.repository;

import com.aduanas.msviajeros.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByViajeroId(String viajeroId);
    List<Documento> findByEstado(String estado);
}