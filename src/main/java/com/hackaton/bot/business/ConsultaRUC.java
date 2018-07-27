package com.hackaton.bot.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaRUC {

    private Integer idEmpresa;
    private List<ConsultaProductos> productos;

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public List<ConsultaProductos> getProductos() {
        return productos;
    }

    public void setProductos(List<ConsultaProductos> productos) {
        this.productos = productos;
    }
}
