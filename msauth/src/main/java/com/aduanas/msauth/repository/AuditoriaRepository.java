package com.aduanas.msauth.repository;

import com.aduanas.msauth.model.AuditoriaAcceso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaRepository
        extends JpaRepository<AuditoriaAcceso, Long> {
}