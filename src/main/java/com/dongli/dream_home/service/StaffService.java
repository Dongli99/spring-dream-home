package com.dongli.dream_home.service;

import java.sql.Date;

import org.springframework.stereotype.Service;

import com.dongli.dream_home.dto.StaffRequest;
import com.dongli.dream_home.dto.StaffResponse;
import com.dongli.dream_home.model.Staff;
import com.dongli.dream_home.repository.StaffRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffService {

    private final StaffRepository staffRepository;

    public void createStaffUsingDefault(StaffRequest staffRequest) {
        Staff staff = mapToStaff(staffRequest);
        Staff savedStaff = staffRepository.save(staff);
        StaffResponse staffResponse = mapToResponse(savedStaff);
        log.info("Staff {} saved.", staffResponse.getStaffNo());
    }

    public void createStaffUsingProcedure(StaffRequest staffRequest) {
        Staff staff = mapToStaff(staffRequest);
        Staff savedStaff = staffRepository.hireStaff(staff);
        StaffResponse staffResponse = mapToResponse(savedStaff);
        log.info("Staff {} saved.", staffResponse.getStaffNo());
    }

    private Staff mapToStaff(StaffRequest staffRequest) {
        return Staff.builder()
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
    }

    private StaffResponse mapToResponse(Staff staff) {
        return StaffResponse.builder()
                .staffNo(staff.getStaffNo())
                .fName(staff.getFName())
                .lName(staff.getLName())
                .position(staff.getPosition())
                .sex(staff.getSex())
                .dob(staff.getDob())
                .salary(staff.getSalary())
                .branchNo(staff.getBranchNo())
                .telephone(staff.getTelephone())
                .mobile(staff.getMobile())
                .email(staff.getEmail())
                .build();
    }

}
