package com.aduanas.msviajeros.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ViajeroRequestDTO {

    @NotBlank
    private String rut;

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    private String documento;

    private String nacionalidad;

    private String vehiculo;

    private String patente;

    @NotBlank
    private String origen;

    @NotBlank
    private String destino;

    private String estado;

    private String riesgo;
}
