package com.rale.tavyraiList.rest;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @GetMapping("/")
    public String welcome(){
        return "Hello World";
    }
}
