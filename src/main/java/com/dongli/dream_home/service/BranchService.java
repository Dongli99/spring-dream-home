package com.dongli.dream_home.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dongli.dream_home.dto.AddressResponse;
import com.dongli.dream_home.dto.BranchRequest;
import com.dongli.dream_home.dto.BranchResponse;
import com.dongli.dream_home.exception.EntityNotFoundException;
import com.dongli.dream_home.model.Branch;
import com.dongli.dream_home.repository.BranchRepository;

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
            throw new IllegalArgumentException("BranchNo can not be null.");
        Optional<Branch> optionalBranch = branchRepository.findById(branchNo);
        if (optionalBranch.isPresent()) {
            Branch branch = optionalBranch.get();
            AddressResponse address = mapTAddressResponse(branch);
            return address;
        } else {
            throw new EntityNotFoundException("Branch not exists.");
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
