package com.dongli.dream_home.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.dongli.dream_home.dto.BranchRequest;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BranchControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    private BranchRequest branchRequest;

    private

    @BeforeEach void setUp() {
        cleanupDatabase();
        // setup request data
        branchRequest = BranchRequest.builder()
                .branchNo("T888")
                .city("Toronto")
                .street("123 Main Street")
                .postCode("T0T 0T0")
                .build();
    }

    @AfterEach
    void tearDown() {
        cleanupDatabase();
    }

    @Test
    void shouldCreateABranchIfNoConflicts() {
        ResponseEntity<Void> createResponseEntity = restTemplate
                .postForEntity("/api/branch", branchRequest, Void.class);
        assertThat(createResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        // check existence of the branch
        ResponseEntity<String> getResponseEntity = restTemplate
                .getForEntity("/api/branch/" + branchRequest.getBranchNo(), String.class);
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // check consistence of data
        DocumentContext documentContext = JsonPath.parse(getResponseEntity.getBody());
        String postCodeInDatabase = documentContext.read("$.postCode");
        assertThat(postCodeInDatabase).isEqualTo(branchRequest.getPostCode());
    }

    @Test
    void shouldNotCreateADuplicatedBranch() {

    }

    @Test
    void shouldReturnTheAddressIfBranchExists() {
        restTemplate.postForEntity("/api/branch", branchRequest, Void.class);

    }

    @Test
    void shouldNotReturnTheAddressIfBranchNotExists() {

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
        try {
            restTemplate.delete("/api/branch/T888");
        } catch (HttpClientErrorException e) {
            // Ignore if the staff member does not exist
            if (e.getStatusCode() != HttpStatus.NOT_FOUND)
                throw e;
        }
    }
}
