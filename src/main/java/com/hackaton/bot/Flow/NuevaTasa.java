package com.hackaton.bot.Flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NuevaTasa {

    private Integer idEmpresa = 7;

    private Integer idProducto = 2 ;

    private Double tasaSoles;

    private Double tasaDolares;

    private String fechaVigencia = "29-07-2018";

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Double getTasaSoles() {
        return tasaSoles;
    }

    public void setTasaSoles(Double tasaSoles) {
        this.tasaSoles = tasaSoles;
    }

    public Double getTasaDolares() {
        return tasaDolares;
    }

    public void setTasaDolares(Double tasaDolares) {
        this.tasaDolares = tasaDolares;
    }

    public String getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(String fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }
}
