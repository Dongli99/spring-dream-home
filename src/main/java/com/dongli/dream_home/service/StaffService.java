package com.dongli.dream_home.service;

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
                .staffno(staffRequest.getStaffno())
                .fname(staffRequest.getFname())
                .lname(staffRequest.getLname())
                .position(staffRequest.getPosition())
                .sex(staffRequest.getSex())
                .dob(staffRequest.getDob())
                .salary(staffRequest.getSalary())
                .branchno(staffRequest.getBranchno())
                .telephone(staffRequest.getTelephone())
                .mobile(staffRequest.getMobile())
                .email(staffRequest.getEmail())
                .build();
        staff.setStaffno(staffRequest.getStaffno());
        staffRepository.save(staff);
        log.info("Staff {} is saved.", staff.getStaffno());
    }
}
