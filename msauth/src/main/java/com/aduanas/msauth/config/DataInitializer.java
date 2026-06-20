package com.aduanas.msauth.config;

import com.aduanas.msauth.model.Rol;
import com.aduanas.msauth.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;

    @Override
    public void run(String... args) {

        crearRol("ADMIN");
        crearRol("FUNCIONARIO");
        crearRol("VIAJERO");
    }

    private void crearRol(String nombre) {

        if (rolRepository.findByNombre(nombre).isEmpty()) {

            Rol rol = new Rol();
            rol.setNombre(nombre);

            rolRepository.save(rol);
        }
    }
}