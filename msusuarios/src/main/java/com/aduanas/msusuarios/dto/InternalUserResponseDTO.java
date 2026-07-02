package com.aduanas.msusuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternalUserResponseDTO {

    private Long id;

    private String email;

    private String rol;

    private String passwordTemporal;

    private String mensaje;

}