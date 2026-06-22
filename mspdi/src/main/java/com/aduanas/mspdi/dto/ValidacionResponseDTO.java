package com.aduanas.mspdi.dto;

public class ValidacionResponseDTO {
    private Long id;
    private String viajeroId;
    private String entidad;
    private String descripcion;
    private String estado;

    public ValidacionResponseDTO() {}
    public ValidacionResponseDTO(Long id, String viajeroId, String entidad, String descripcion, String estado) {
        this.id = id;
        this.viajeroId = viajeroId;
        this.entidad = entidad;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getViajeroId() { return viajeroId; }
    public void setViajeroId(String viajeroId) { this.viajeroId = viajeroId; }
    public String getEntidad() { return entidad; }
    public void setEntidad(String entidad) { this.entidad = entidad; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}