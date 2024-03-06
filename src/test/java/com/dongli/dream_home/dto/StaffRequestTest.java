package com.dongli.dream_home.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class StaffRequestTest {

    @Test
    void testAllFieldsCanBeSetAndGet() {
        // Create a StaffRequest object using the builder
        StaffRequest staffRequest = StaffRequest.builder()
                .staffNo("123")
                .fName("John")
                .lName("Doe")
                .position("Manager")
                .sex("M")
                .dob(LocalDate.of(1990, 1, 1))
                .salary(50000)
                .branchNo("Branch1")
                .telephone("123456789")
                .mobile("9876543210")
                .email("john.doe@example.com")
                .build();

        // Verify that all fields can be set and get
        assertThat(staffRequest.getStaffNo()).isEqualTo("123");
        assertThat(staffRequest.getFName()).isEqualTo("John");
        assertThat(staffRequest.getLName()).isEqualTo("Doe");
        assertThat(staffRequest.getPosition()).isEqualTo("Manager");
        assertThat(staffRequest.getSex()).isEqualTo("M");
        assertThat(staffRequest.getDob()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(staffRequest.getSalary()).isEqualTo(50000);
        assertThat(staffRequest.getBranchNo()).isEqualTo("Branch1");
        assertThat(staffRequest.getTelephone()).isEqualTo("123456789");
        assertThat(staffRequest.getMobile()).isEqualTo("9876543210");
        assertThat(staffRequest.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testNoArgsConstructor() {
        // Create a StaffRequest object using the no-args constructor
        StaffRequest staffRequest = new StaffRequest();

        // Verify that all fields are initially null or default values
        assertThat(staffRequest.getStaffNo()).isNull();
        assertThat(staffRequest.getFName()).isNull();
        assertThat(staffRequest.getLName()).isNull();
        assertThat(staffRequest.getPosition()).isNull();
        assertThat(staffRequest.getSex()).isNull();
        assertThat(staffRequest.getDob()).isNull();
        assertThat(staffRequest.getSalary()).isEqualTo(0);
        assertThat(staffRequest.getBranchNo()).isNull();
        assertThat(staffRequest.getTelephone()).isNull();
        assertThat(staffRequest.getMobile()).isNull();
        assertThat(staffRequest.getEmail()).isNull();
    }
}