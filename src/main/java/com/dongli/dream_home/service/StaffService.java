package com.dongli.dream_home.service;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.dongli.dream_home.dto.StaffRequest;
import com.dongli.dream_home.model.Staff;
import com.dongli.dream_home.repository.StaffRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

    public void createStaffUsingDefault(StaffRequest staffRequest) {
        Staff staff = requestToStaff(staffRequest);
        staffRepository.save(staff);
    }

    public void createStaffUsingProcedure(StaffRequest staffRequest) {
        Staff staff = requestToStaff(staffRequest);
        staffRepository.hireStaff(staff);
    }

    private Staff requestToStaff(StaffRequest staffRequest) {
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
        return staff;
    }
}
