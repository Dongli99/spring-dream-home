package com.dongli.dream_home.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.dongli.dream_home.dto.StaffRequest;
import com.dongli.dream_home.dto.StaffResponse;
import com.dongli.dream_home.service.BranchService;
import com.dongli.dream_home.service.StaffService;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

}
