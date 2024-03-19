package com.dongli.dream_home.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class BranchTest {
    Branch branchNoPara = new Branch();
    Branch branchAllPara = new Branch("B001", "123 Main St", "City", "12345");

    @Test
    void testBranchCanBeCreatedWithNoAndAllParameters() {
        assertNotNull(branchNoPara);
        assertNotNull(branchAllPara);
    }

    @Test
    void testBasicFieldsCanBeSet() {
        assertThat(branchAllPara.getStreet()).isEqualTo("123 Main St");
        branchAllPara.setStreet("456 Elm St");
        assertThat(branchAllPara.getStreet()).isEqualTo("456 Elm St");

        assertThat(branchAllPara.getCity()).isEqualTo("City");
        branchAllPara.setCity("Town");
        assertThat(branchAllPara.getCity()).isEqualTo("Town");

        assertThat(branchAllPara.getPostCode()).isEqualTo("12345");
        branchAllPara.setPostCode("54321");
        assertThat(branchAllPara.getPostCode()).isEqualTo("54321");
    }
}
