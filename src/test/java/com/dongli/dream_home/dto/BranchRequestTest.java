package com.dongli.dream_home.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class BranchRequestTest {
    @Test
    void testAllFieldsCanBeSetAndGet() {
        // Create a BranchRequest object using the builder
        BranchRequest branchRequest = BranchRequest.builder()
                .branchNo("Branch1")
                .street("123 Main St")
                .city("City")
                .postCode("12345")
                .build();

        // Verify that all fields can be set and get
        assertThat(branchRequest.getBranchNo()).isEqualTo("Branch1");
        assertThat(branchRequest.getStreet()).isEqualTo("123 Main St");
        assertThat(branchRequest.getCity()).isEqualTo("City");
        assertThat(branchRequest.getPostCode()).isEqualTo("12345");
    }

    @Test
    void testNoArgsConstructor() {
        // Create a BranchRequest object using the no-args constructor
        BranchRequest branchRequest = new BranchRequest();

        // Verify that all fields are initially null or default values
        assertThat(branchRequest.getBranchNo()).isNull();
        assertThat(branchRequest.getStreet()).isNull();
        assertThat(branchRequest.getCity()).isNull();
        assertThat(branchRequest.getPostCode()).isNull();
    }
}
