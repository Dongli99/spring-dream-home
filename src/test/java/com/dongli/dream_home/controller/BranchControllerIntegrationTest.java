package com.dongli.dream_home.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BranchControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    private

    @BeforeEach void setUp() {

    }

    @Test
    void shouldReturnTheAddressIfBranchExists() {

    }

    @Test
    void shouldNotReturnTheAddressIfBranchNotExists() {

    }

    @Test
    void shouldCreateABranchIfNoConflicts() {

    }

    @Test
    void shouldNotCreateADuplicatedBranch() {

    }

    @Test
    void shouldUpdateAnExistingBranch() {

    }

    @Test
    void shouldNotUpdateANotExistingBranch() {

    }

    @Test
    void shouldNotUpdateABranchIfIdIsModified() {

    }

    private void cleanupDatabase() {

    }
}
