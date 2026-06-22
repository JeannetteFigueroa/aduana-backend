package com.aduanas.msviajeros.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "documentos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String viajeroId;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String nombre;

    private Long tamano;

    @Column(nullable = false)
    private String estado;

    private String observacion;

    private String url;

    private String fechaSubida;
}