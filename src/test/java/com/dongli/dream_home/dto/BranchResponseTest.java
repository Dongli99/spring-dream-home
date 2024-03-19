package com.dongli.dream_home.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class BranchResponseTest {
    @Test
    void testAllFieldsCanBeSetAndGet() {
        // Create a BranchResponse object using the builder
        BranchResponse branchResponse = BranchResponse.builder()
                .branchNo("Branch1")
                .street("123 Main St")
                .city("City")
                .postCode("12345")
                .build();

        // Verify that all fields can be set and get
        assertThat(branchResponse.getBranchNo()).isEqualTo("Branch1");
        assertThat(branchResponse.getStreet()).isEqualTo("123 Main St");
        assertThat(branchResponse.getCity()).isEqualTo("City");
        assertThat(branchResponse.getPostCode()).isEqualTo("12345");
    }

    @Test
    void testNoArgsConstructor() {
        // Create a BranchResponse object using the no-args constructor
        BranchResponse branchResponse = new BranchResponse();

        // Verify that all fields are initially null or default values
        assertThat(branchResponse.getBranchNo()).isNull();
        assertThat(branchResponse.getStreet()).isNull();
        assertThat(branchResponse.getCity()).isNull();
        assertThat(branchResponse.getPostCode()).isNull();
    }
}
