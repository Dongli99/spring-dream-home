package com.dongli.dream_home.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dongli.dream_home.dto.StaffRequest;
import com.dongli.dream_home.service.StaffService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createStaff(
            @RequestBody StaffRequest staffRequest) {
        staffService.createStaff(staffRequest);
    }
}
