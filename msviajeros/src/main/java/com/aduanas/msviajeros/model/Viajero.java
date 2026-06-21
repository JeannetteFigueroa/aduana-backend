package com.aduanas.msviajeros.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "viajeros")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Viajero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rut;

    private String nombres;

    private String apellidos;

    private String documento;

    private String nacionalidad;

    private String vehiculo;

    private String patente;

    private String origen;

    private String destino;

    private String estado;

    private String riesgo;

    private LocalDateTime fechaIngreso;
}
