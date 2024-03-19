package com.dongli.dream_home.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.dongli.dream_home.dto.StaffRequest;
import com.dongli.dream_home.dto.StaffResponse;
import com.dongli.dream_home.exception.EntityNotFoundException;
import com.dongli.dream_home.exception.ForeignKeyConstraintViolationException;
import com.dongli.dream_home.exception.InconsistentDataException;
import com.dongli.dream_home.model.Staff;
import com.dongli.dream_home.repository.BranchRepository;
import com.dongli.dream_home.repository.StaffRepository;

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

}
