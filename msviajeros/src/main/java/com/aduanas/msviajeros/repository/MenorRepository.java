package com.aduanas.msviajeros.repository;

import com.aduanas.msviajeros.model.Menor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenorRepository extends JpaRepository<Menor, Long> {
        List<Menor> findByViajeroId(Long viajeroId);
}