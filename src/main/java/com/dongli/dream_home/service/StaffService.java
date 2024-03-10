package com.dongli.dream_home.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.dongli.dream_home.dto.StaffRequest;
import com.dongli.dream_home.dto.StaffResponse;
import com.dongli.dream_home.exception.EntityNotFoundException;
import com.dongli.dream_home.exception.ForeignKeyConstraintViolationException;
import com.dongli.dream_home.exception.InconsistentDataException;
import com.dongli.dream_home.model.Staff;
import com.dongli.dream_home.repository.StaffRepository;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffResponse createStaffUsingProcedure(StaffRequest staffRequest) {
        Optional<Staff> optionalStaff = staffRepository.findById(staffRequest.getStaffNo());
        if (optionalStaff.isPresent())
            throw new EntityExistsException("Staff " + staffRequest.getStaffNo() + " already exists.");
        Staff savedStaff = staffRepository.hireStaff(mapToStaff(staffRequest));
        StaffResponse staffResponse = mapToResponse(savedStaff);
        log.info("Staff {} saved.", staffResponse.getStaffNo());
        return staffResponse;
    }

    public List<StaffResponse> getAllStaffs() {
        List<Staff> staffs = staffRepository.findAll();
        return staffs.stream().map(this::mapToResponse).toList();
    }

    public StaffResponse findById(String id) {
        Optional<Staff> optionalStaff = staffRepository.findById(id);
        if (optionalStaff.isPresent()) {
            Staff staff = optionalStaff.get();
            StaffResponse response = mapToResponse(staff);
            return response;
        } else {
            throw new EntityNotFoundException("Staff with id " + id + " not found.");
        }
    }

    public void deleteById(String id) {
        StaffResponse staffResponse = findById(id);
        try {
            staffRepository.deleteById(staffResponse.getStaffNo());
            log.info("Staff {} deleted.", staffResponse.getStaffNo());
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException(
                    "Cannot delete staff " + id + " due to existing related records");
        }
    }

    public void updateById(String id, StaffRequest staffRequest) {
        StaffResponse staffResponse = findById(id);
        if (staffRequest.getStaffNo().equals(staffResponse.getStaffNo())) {
            Staff staffToUpdate = mapToStaff(staffRequest);
            staffRepository.save(staffToUpdate);
        } else {
            throw new InconsistentDataException(
                    "Identifier StaffNo cannot be changed.");
        }
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
