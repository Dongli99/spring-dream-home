package com.dongli.dream_home.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.dongli.dream_home.dto.StaffRequest;
import com.dongli.dream_home.model.Staff;
import com.dongli.dream_home.repository.StaffRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

    public void createStaff(StaffRequest staffRequest) {
        Staff staff = Staff.builder()
                .staffNo(staffRequest.getStaffNo())
                .fName(staffRequest.getFName())
                .lName(staffRequest.getLName())
                .position(staffRequest.getPosition())
                .sex(staffRequest.getSex())
                .dob(staffRequest.getDob())
                .salary(staffRequest.getSalary())
                .branchNo(staffRequest.getBranchNo())
                .telephone(staffRequest.getTelephone())
                .mobile(staffRequest.getMobile())
                .email(staffRequest.getEmail())
                .build();
        staffRepository.save(staff);
        log.info("Staff {} is saved.", staff.getStaffNo());
    }
}
