package com.aduanas.msviajeros.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CambioRiesgoDTO {

    @NotBlank
    private String riesgo;
}
