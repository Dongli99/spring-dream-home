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
import com.dongli.dream_home.repository.StaffRepository;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StaffService {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private final StaffRepository staffRepository;

    public StaffResponse createStaffUsingProcedure(StaffRequest staffRequest) {
        log.info("Active Profile: {}.", activeProfile);

        Optional<Staff> optionalStaff = staffRepository.findById(staffRequest.getStaffNo());
        if (optionalStaff.isPresent()) { // In case the staff is already in the database
            throw new EntityExistsException("Staff " + staffRequest.getStaffNo() + " already exists.");
        }

        try {
            // If Oracle is active, execute the stored procedure
            Staff savedStaff = activeProfile.equals("h2")
                    ? staffRepository.save(mapToStaff(staffRequest))
                    : staffRepository.hireStaff(mapToStaff(staffRequest));
            StaffResponse staffResponse = mapToResponse(savedStaff);
            log.info("Staff {} saved.", staffResponse.getStaffNo());
            return staffResponse;
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException(
                    "BranchNo " + staffRequest.getBranchNo() + " may not existed.");
        }
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
        } catch (DataIntegrityViolationException de) {
            throw new ForeignKeyConstraintViolationException(
                    "Cannot delete staff " + id + " due to existing related records");
        }
    }

    public void updateById(String id, StaffRequest staffRequest) {
        StaffResponse staffResponse = findById(id);
        if (staffRequest.getStaffNo().equals(staffResponse.getStaffNo())) {
            Staff staffToUpdate = mapToStaff(staffRequest);
            // save data and handle foreign key BranchNo exception
            try {
                staffRepository.save(staffToUpdate);
                log.info("Staff {} updated.", staffToUpdate.getStaffNo());
            } catch (DataIntegrityViolationException e) {
                throw new ForeignKeyConstraintViolationException(
                        "BranchNo " + staffRequest.getBranchNo() + " may not existed.");
            }
        } else {
            throw new InconsistentDataException(
                    "StaffNo must be the same.");
        }
    }

    public Staff mapToStaff(StaffRequest staffRequest) {
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

    public StaffResponse mapToResponse(Staff staff) {
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
