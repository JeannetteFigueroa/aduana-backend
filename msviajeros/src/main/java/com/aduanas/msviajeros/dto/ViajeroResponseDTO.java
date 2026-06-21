package com.aduanas.msviajeros.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViajeroResponseDTO {

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

    private String fechaIngreso;
}
