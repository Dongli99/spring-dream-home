package com.dongli.dream_home.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class StaffResponseTest {

    @Test
    void testAllFieldsCanBeSetAndGet() {
        // Create a StaffResponse object using the builder
        StaffResponse staffResponse = StaffResponse.builder()
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
        assertThat(staffResponse.getStaffNo()).isEqualTo("123");
        assertThat(staffResponse.getFName()).isEqualTo("John");
        assertThat(staffResponse.getLName()).isEqualTo("Doe");
        assertThat(staffResponse.getPosition()).isEqualTo("Manager");
        assertThat(staffResponse.getSex()).isEqualTo("M");
        assertThat(staffResponse.getDob()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(staffResponse.getSalary()).isEqualTo(50000);
        assertThat(staffResponse.getBranchNo()).isEqualTo("Branch1");
        assertThat(staffResponse.getTelephone()).isEqualTo("123456789");
        assertThat(staffResponse.getMobile()).isEqualTo("9876543210");
        assertThat(staffResponse.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testNoArgsConstructor() {
        // Create a StaffResponse object using the no-args constructor
        StaffResponse staffResponse = new StaffResponse();

        // Verify that all fields are initially null or default values
        assertThat(staffResponse.getStaffNo()).isNull();
        assertThat(staffResponse.getFName()).isNull();
        assertThat(staffResponse.getLName()).isNull();
        assertThat(staffResponse.getPosition()).isNull();
        assertThat(staffResponse.getSex()).isNull();
        assertThat(staffResponse.getDob()).isNull();
        assertThat(staffResponse.getSalary()).isEqualTo(0);
        assertThat(staffResponse.getBranchNo()).isNull();
        assertThat(staffResponse.getTelephone()).isNull();
        assertThat(staffResponse.getMobile()).isNull();
        assertThat(staffResponse.getEmail()).isNull();
    }
}