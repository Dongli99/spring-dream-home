package com.dongli.dream_home.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dongli.dream_home.dto.AddressResponse;
import com.dongli.dream_home.dto.BranchRequest;
import com.dongli.dream_home.dto.BranchResponse;
import com.dongli.dream_home.exception.EntityNotFoundException;
import com.dongli.dream_home.exception.InconsistentDataException;
import com.dongli.dream_home.model.Branch;
import com.dongli.dream_home.repository.BranchRepository;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BranchService {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private final BranchRepository branchRepository;

    public AddressResponse findBranchAddressById(String branchNo) {
        Branch existedBranch = findById(branchNo);
        if (existedBranch == null)
            throw new EntityNotFoundException("Branch not exists.");
        return mapTAddressResponse(existedBranch);
    }

    public BranchResponse findBranchById(String branchNo) {
        Branch existedBranch = findById(branchNo);
        if (existedBranch == null)
            throw new EntityNotFoundException("Branch not exists.");
        return mapToResponse(existedBranch);
    }

    public BranchResponse addNewBranch(BranchRequest branchRequest) {
        if (branchRequest.getBranchNo() == null)
            throw new IllegalArgumentException("Branch No. cannot be null.");

        // check existence before creation
        Branch existedBranch = findById(branchRequest.getBranchNo());
        if (existedBranch != null)
            throw new EntityExistsException("Branch " + branchRequest.getBranchNo() + " already exists.");

        // decide whether to use procedure according to the profile
        if (activeProfile.equals("h2"))
            branchRepository.save(mapToBranch(branchRequest));
        else
            branchRepository.newBranch(mapToBranch(branchRequest));

        // check existence after creation
        Branch savedBranch = findById(branchRequest.getBranchNo());
        if (savedBranch == null)
            throw new EntityNotFoundException("Failed to save branch.");

        // return response
        BranchResponse response = mapToResponse(savedBranch);
        log.info("Branch {} saved.", response.getBranchNo());
        return response;
    }

    public void updateById(String branchNo, BranchRequest branchRequest) {
        // check consistence
        if (!branchNo.equals(branchRequest.getBranchNo()))
            throw new InconsistentDataException("BranchNo cannot be changed");

        // update if exists
        Branch branchToUpdate = findById(branchNo);
        if (branchToUpdate == null)
            throw new EntityNotFoundException("Branch " + branchNo + " not exists.");
        branchRepository.save(mapToBranch(branchRequest));
    }

    private Branch findById(String branchNo) {
        // a reusable method to support other methods in class.
        if (branchNo == null)
            throw new IllegalArgumentException("BranchNo cannot be null.");
        Optional<Branch> optionalBranch = branchRepository.findById(branchNo);
        if (optionalBranch.isPresent()) {
            Branch branch = optionalBranch.get();
            return branch;
        } else {
            return null;
        }
    }

    private Branch mapToBranch(BranchRequest branchRequest) {
        return Branch.builder()
                .branchNo(branchRequest.getBranchNo())
                .street(branchRequest.getStreet())
                .city(branchRequest.getCity())
                .postCode(branchRequest.getPostCode())
                .build();
    }

    private BranchResponse mapToResponse(Branch branch) {
        return BranchResponse.builder()
                .branchNo(branch.getBranchNo())
                .street(branch.getStreet())
                .city(branch.getCity())
                .postCode(branch.getPostCode())
                .build();
    }

    private AddressResponse mapTAddressResponse(Branch branch) {
        return AddressResponse.builder()
                .city(branch.getCity())
                .street(branch.getStreet())
                .postCode(branch.getPostCode())
                .build();
    }
}
