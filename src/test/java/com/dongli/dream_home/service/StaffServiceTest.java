package com.dongli.dream_home.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dongli.dream_home.dto.StaffRequest;
import com.dongli.dream_home.dto.StaffResponse;
import com.dongli.dream_home.model.Staff;
import com.dongli.dream_home.repository.StaffRepository;

@ExtendWith(MockitoExtension.class)
public class StaffServiceTest {

    private StaffRequest staffRequest;
    private Staff staff;

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private StaffService staffService;

    @BeforeEach
    void setUp() {
        staffRequest = new StaffRequest("S123", "John", "Doe", "Manager", "Male",
                LocalDate.of(1990, 1, 1), 50000, "B001",
                "1234567890", "9876543210", "john.doe@example.com");
        staff = new Staff("S123", "John", "Doe", "Manager", "Male",
                LocalDate.of(1990, 1, 1), 50000, "B001",
                "1234567890", "9876543210", "john.doe@example.com");
    }

    @Test
    void testCreateStaffUsingProcedure() {
        when(staffRepository.hireStaff(any(Staff.class))).thenReturn(staff);
        staffService.createStaffUsingProcedure(staffRequest);
        verify(staffRepository).hireStaff(any(Staff.class));
    }

    @Test
    void testGetAllStaffs() {
        List<Staff> staffList = List.of(staff);
        when(staffRepository.findAll()).thenReturn(staffList);
        List<StaffResponse> expectedResponseList = List.of(staffService.mapToResponse(staff));
        List<StaffResponse> actualResponseList = staffService.getAllStaffs();
        assertThat(actualResponseList).isEqualTo(expectedResponseList);
    }

    @Test
    void testFindById() {
        when(staffRepository.findById(anyString())).thenReturn(Optional.of(staff));
        StaffResponse expectedResponse = staffService.mapToResponse(staff);
        StaffResponse actualResponse = staffService.findById("S123");
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void testDeleteById() {
        when(staffRepository.findById(anyString())).thenReturn(Optional.of(staff));
        // Verify that the staffRepository's deleteById method is called once with the
        // correct parameter
        staffService.deleteById("S123");
        verify(staffRepository, times(1)).deleteById("S123");
    }

    @Test
    void testUpdateById() {
        when(staffRepository.findById(anyString())).thenReturn(Optional.of(staff));
        StaffRequest updatedStaffRequest = new StaffRequest("S123", "John", "Doe", "Manager", "Male",
                LocalDate.of(1990, 1, 1), 60000, "B002",
                "1234567890", "9876543210", "john.doe@example.com");
        // Verify that the staffRepository's save method is called once with the correct
        // parameter
        staffService.updateById("S123", updatedStaffRequest);
        verify(staffRepository, times(1)).save(any(Staff.class));
    }
}
