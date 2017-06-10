package com.example.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by niket.shah on 10/6/17.
 */
@RestController
public class ClientRestController {
    
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/client")
    public String callServer(){
        ResponseEntity<String> entity = restTemplate.getForEntity("https://localhost:8081/server", String.class);
        return entity.getBody();
    }
}
