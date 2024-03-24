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
        if (branchNo == null)
            throw new IllegalArgumentException("BranchNo cannot be null.");
        Optional<Branch> optionalBranch = branchRepository.findById(branchNo);
        if (optionalBranch.isPresent()) {
            Branch branch = optionalBranch.get();
            AddressResponse address = mapTAddressResponse(branch);
            return address;
        } else {
            throw new EntityNotFoundException("Branch not exists.");
        }
    }

    public BranchResponse addNewBranch(BranchRequest branchRequest) {
        if (branchRequest.getBranchNo() == null)
            throw new IllegalArgumentException("Branch No. cannot be null.");

        // check existence before creation
        Optional<Branch> optionalBranch = branchRepository.findById(branchRequest.getBranchNo());
        if (optionalBranch.isPresent()) {
            throw new EntityExistsException("Branch " + branchRequest.getBranchNo() + " already exists.");
        }
        if (activeProfile.equals("h2")) // avoid use procedure if H2 is used.
            branchRepository.save(mapToBranch(branchRequest));
        else
            branchRepository.newBranch(mapToBranch(branchRequest));

        // check existence after creation
        Optional<Branch> optionalSavedBranch = branchRepository.findById(branchRequest.getBranchNo());
        if (!optionalSavedBranch.isPresent())
            throw new EntityNotFoundException("Failed to save branch.");
        BranchResponse response = mapToResponse(optionalSavedBranch.get());
        log.info("Branch {} saved.", response.getBranchNo());
        return response;
    }

    public void updateById(String branchNo, BranchRequest branchRequest) {
        // check consistence
        if (!branchNo.equals(branchRequest.getBranchNo()))
            throw new InconsistentDataException("BranchNo cannot be changed");

        // update if exists
        Optional<Branch> branchToUpdate = branchRepository.findById(branchNo);
        if (!branchToUpdate.isPresent())
            throw new EntityNotFoundException("Branch " + branchNo + " not exists.");
        Branch branch = mapToBranch(branchRequest);
        branchRepository.save(branch);
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
