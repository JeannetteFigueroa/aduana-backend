package com.aduanas.msusuarios.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FuncionarioResponseDTO {

    private Long id;

    private String rut;

    private String nombres;

    private String apellidos;

    private String correoInstitucional;

    private String rol;

    private String estado;
}
