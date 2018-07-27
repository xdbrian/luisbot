package com.hackaton.bot.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestController {

    private String urlHost = "http://192.168.43.188";

    public RestTemplate getTasas(String dni) {
        String url = String.format("%s:8080/consultarDNI?dni=%s",urlHost,dni);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        return restTemplate;
    }

    public Object saveTasa() {
        String url = String.format("%s:8080/save",urlHost);
        RestTemplate restTemplate = new RestTemplate();
        //ResponseEntity<String> response = restTemplate.postForObject(url, "",String.class);
        return null;
    }
}
