package com.dongli.dream_home.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongli.dream_home.dto.AddressResponse;
import com.dongli.dream_home.service.BranchService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping("/address/{id}")
    public ResponseEntity<AddressResponse> searchAddressById(@PathVariable("id") String branchNo) {
        AddressResponse branchAddress = branchService.findBranchAddressById(branchNo);
        return ResponseEntity.ok(branchAddress);
    }
}
