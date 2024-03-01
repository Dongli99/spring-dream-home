package com.dongli.dream_home.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class StaffController {
    @GetMapping
    public String getMethodName() {
        return "Welcome";
    }
}
