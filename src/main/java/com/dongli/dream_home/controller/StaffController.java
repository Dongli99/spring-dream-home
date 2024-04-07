package com.dongli.dream_home.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.dongli.dream_home.dto.StaffRequest;
import com.dongli.dream_home.dto.StaffResponse;
import com.dongli.dream_home.service.StaffService;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    public ResponseEntity<Void> createStaff(
            @RequestBody StaffRequest staffRequest,
            UriComponentsBuilder ucb) {
        StaffResponse staffResponse = staffService
                .createStaffUsingProcedure(staffRequest);
        URI locationOfNewStaff = ucb
                .path("/api/staff/{id}")
                .buildAndExpand(staffResponse.getStaffNo())
                .toUri();
        return ResponseEntity.created(locationOfNewStaff).build();
    }

    @GetMapping
    public ResponseEntity<List<StaffResponse>> listAllStaffs() {
        List<StaffResponse> staffResponses = staffService.getAllStaffs();
        return ResponseEntity.ok(staffResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<StaffResponse> findStaffById(@PathVariable String id) {
        StaffResponse staffResponse = staffService.findById(id);
        return ResponseEntity.ok(staffResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStaffById(@PathVariable String id) {
        staffService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateStaff(
            @PathVariable String id,
            @RequestBody StaffRequest staffRequest) {
        staffService.updateById(id, staffRequest);
        return ResponseEntity.noContent().build();
    }

}
