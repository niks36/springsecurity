package com.example.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by niket.shah on 1/7/17.
 */
@RestController
public class ServerRestController {

    @GetMapping(value = "/server")
    public String serverCall(){
        return "It is server call";
    }
}
