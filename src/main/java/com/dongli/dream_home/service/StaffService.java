package com.dongli.dream_home.service;

import java.sql.Date;
import java.util.List;

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

    public StaffResponse createStaffUsingProcedure(StaffRequest staffRequest) {
        Staff savedStaff = staffRepository.hireStaff(mapToStaff(staffRequest));
        StaffResponse staffResponse = mapToResponse(savedStaff);
        log.info("Staff {} saved.", staffResponse.getStaffNo());
        return staffResponse;
    }

    public List<StaffResponse> getAllStaffs() {
        List<Staff> staffs = staffRepository.findAll();
        return staffs.stream().map(this::mapToResponse).toList();
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
