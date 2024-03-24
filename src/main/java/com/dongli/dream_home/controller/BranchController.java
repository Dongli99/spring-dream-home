package com.dongli.dream_home.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.dongli.dream_home.dto.AddressResponse;
import com.dongli.dream_home.dto.BranchRequest;
import com.dongli.dream_home.dto.BranchResponse;
import com.dongli.dream_home.service.BranchService;

import lombok.RequiredArgsConstructor;

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

    @PostMapping
    public ResponseEntity<Void> openNewBranch(
            @RequestBody BranchRequest branchRequest,
            UriComponentsBuilder ucb) {
        BranchResponse branchResponse = branchService.addNewBranch(branchRequest);
        URI locationOfNewBranch = ucb
                .path("/api/branch/{id}")
                .buildAndExpand(branchResponse.getBranchNo())
                .toUri();
        return ResponseEntity.created(locationOfNewBranch).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateBranch(
            @PathVariable("id") String branchNo,
            @RequestBody BranchRequest branchRequest) {
        branchService.updateById(branchNo, branchRequest);
        return ResponseEntity.noContent().build();
    }
}
