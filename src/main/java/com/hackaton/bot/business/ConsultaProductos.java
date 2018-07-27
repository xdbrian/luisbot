package com.hackaton.bot.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaProductos {

    private Integer idProducto;
    private double tasaSoles;
    private double tasaDolares;

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public double getTasaSoles() {
        return tasaSoles;
    }

    public void setTasaSoles(double tasaSoles) {
        this.tasaSoles = tasaSoles;
    }

    public double getTasaDolares() {
        return tasaDolares;
    }

    public void setTasaDolares(double tasaDolares) {
        this.tasaDolares = tasaDolares;
    }
}
