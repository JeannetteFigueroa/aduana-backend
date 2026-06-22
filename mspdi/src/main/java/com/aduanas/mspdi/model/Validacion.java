package com.aduanas.mspdi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "validaciones")
public class Validacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String viajeroId;
    private String entidad;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.PENDIENTE;

    public enum Estado {
        PENDIENTE, APROBADO, RECHAZADO
    }

    public Validacion() {}
    public Validacion(String viajeroId, String entidad, String descripcion) {
        this.viajeroId = viajeroId;
        this.entidad = entidad;
        this.descripcion = descripcion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getViajeroId() { return viajeroId; }
    public void setViajeroId(String viajeroId) { this.viajeroId = viajeroId; }
    public String getEntidad() { return entidad; }
    public void setEntidad(String entidad) { this.entidad = entidad; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
}