package com.dongli.dream_home.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dongli.dream_home.dto.StaffRequest;
import com.dongli.dream_home.model.Staff;
import com.dongli.dream_home.repository.StaffRepository;

@ExtendWith(MockitoExtension.class)
public class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private StaffService staffService;

    @Test
    public void testCreateStaff() {
        // Given
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setStaffNo("S123");
        staffRequest.setFName("John");
        staffRequest.setLName("Doe");
        staffRequest.setPosition("Manager");
        staffRequest.setSex("Male");
        staffRequest.setDob(LocalDate.of(1990, 1, 1));
        staffRequest.setSalary(50000);
        staffRequest.setBranchNo("B001");
        staffRequest.setTelephone("1234567890");
        staffRequest.setMobile("9876543210");
        staffRequest.setEmail("john.doe@example.com");

        // Mock the behavior of staffRepository.hireStaff() to return a Staff object
        Staff savedStaff = new Staff("S123", "John", "Doe", "Manager", "Male",
                LocalDate.of(1990, 1, 1), 50000, "B001",
                "1234567890", "9876543210", "john.doe@example.com");
        when(staffRepository.hireStaff(any(Staff.class))).thenReturn(savedStaff);

        // When
        staffService.createStaffUsingProcedure(staffRequest);

        // Then
        verify(staffRepository).hireStaff(any(Staff.class));
    }
}
