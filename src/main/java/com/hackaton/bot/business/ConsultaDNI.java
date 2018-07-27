package com.hackaton.bot.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaDNI {

    String estadoHTTP;

    public String getEstadoHTTP() {
        return estadoHTTP;
    }

    public void setEstadoHTTP(String estadoHTTP) {
        this.estadoHTTP = estadoHTTP;
    }
}
