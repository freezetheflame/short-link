package com.example.short_link.linkage.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test")
public class testcontroller {
    @PostMapping("/")
    public String test() {
        return "test";
    }
}
