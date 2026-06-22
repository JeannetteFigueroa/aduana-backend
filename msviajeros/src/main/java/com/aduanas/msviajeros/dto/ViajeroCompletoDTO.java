package com.aduanas.msviajeros.dto;

import java.util.List;

public class ViajeroCompletoDTO {
        private Long id;
        private String nombre;
        private String rut;
        private VehiculoResumenDTO vehiculo;
        private List<DocumentoDTO> documentos;
        private DeclaracionSAGDTO declaracionSag;
        private List<ValidacionDTO> validacionesPdi;
        private List<MenorResumenDTO> menores;

        public ViajeroCompletoDTO() {}

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getRut() { return rut; }
        public void setRut(String rut) { this.rut = rut; }
        public VehiculoResumenDTO getVehiculo() { return vehiculo; }
        public void setVehiculo(VehiculoResumenDTO vehiculo) { this.vehiculo = vehiculo; }
        public List<DocumentoDTO> getDocumentos() { return documentos; }
        public void setDocumentos(List<DocumentoDTO> documentos) { this.documentos = documentos; }
        public DeclaracionSAGDTO getDeclaracionSag() { return declaracionSag; }
        public void setDeclaracionSag(DeclaracionSAGDTO declaracionSag) { this.declaracionSag = declaracionSag; }
        public List<ValidacionDTO> getValidacionesPdi() { return validacionesPdi; }
        public void setValidacionesPdi(List<ValidacionDTO> validacionesPdi) { this.validacionesPdi = validacionesPdi; }
        public List<MenorResumenDTO> getMenores() { return menores; }
        public void setMenores(List<MenorResumenDTO> menores) { this.menores = menores; }
}