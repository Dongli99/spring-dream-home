package com.dongli.dream_home.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.dongli.dream_home.dto.StaffRequest;
import com.dongli.dream_home.dto.StaffResponse;
import com.dongli.dream_home.service.StaffService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@Slf4j
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    public ResponseEntity<Void> createStaff(
            @RequestBody StaffRequest staffRequest,
            UriComponentsBuilder ucb) {
        StaffResponse staffResponse = staffService
                .createStaffUsingProcedure(staffRequest);
        log.info("response in controller: {}", staffResponse.getStaffNo());
        URI locationOfNewStaff = ucb
                .path("/api/staff/{id}")
                .buildAndExpand(staffResponse.getStaffNo())
                .toUri();
        return ResponseEntity.created(locationOfNewStaff).build();
    }

    @GetMapping
    public ResponseEntity<List<StaffResponse>> listAllStaff() {
        List<StaffResponse> staffResponses = staffService.getAllStaffs();
        return ResponseEntity.ok(staffResponses);
    }
}
