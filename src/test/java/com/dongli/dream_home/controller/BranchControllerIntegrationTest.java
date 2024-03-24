package com.dongli.dream_home.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.dongli.dream_home.dto.BranchRequest;
import com.dongli.dream_home.dto.StaffRequest;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BranchControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    private BranchRequest branchRequest;

    private HttpEntity<BranchRequest> httpRequest;

    @BeforeEach
    void setUp() {
        // setup request data
        branchRequest = BranchRequest.builder()
                .branchNo("T888")
                .city("Toronto")
                .street("123 Main Street")
                .postCode("T0T 0T0")
                .build();
        cleanupDatabase();
        httpRequest = new HttpEntity<>(branchRequest);
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
        restTemplate.postForEntity("/api/branch", branchRequest, Void.class); // 1st creation
        ResponseEntity<Void> createResponseEntity = restTemplate // 2nd creation
                .postForEntity("/api/branch", branchRequest, Void.class);
        assertThat(createResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldReturnTheAddressIfBranchExists() {
        restTemplate.postForEntity("/api/branch", branchRequest, Void.class);
        // get address
        ResponseEntity<String> getResponseEntity = restTemplate
                .getForEntity("/api/branch/address/" + branchRequest.getBranchNo(), String.class);
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // check consistence of data
        DocumentContext documentContext = JsonPath.parse(getResponseEntity.getBody());
        String postCodeInDatabase = documentContext.read("$.postCode");
        assertThat(postCodeInDatabase).isEqualTo(branchRequest.getPostCode());
    }

    @Test
    void shouldNotReturnTheAddressIfBranchNotExists() {
        // get address
        ResponseEntity<String> getResponseEntity = restTemplate
                .getForEntity("/api/branch/address/" + branchRequest.getBranchNo(), String.class);
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldDeleteABranchIfIdExistsAndNoForeignKeyConstraints() {
        // create data
        restTemplate.postForEntity("/api/branch", branchRequest, Void.class);
        // check existence before deletion
        ResponseEntity<String> getResponseBefore = restTemplate
                .getForEntity("/api/branch/" + branchRequest.getBranchNo(), String.class);
        assertThat(getResponseBefore.getStatusCode()).isEqualTo(HttpStatus.OK);
        // check existence before deletion
        restTemplate.delete("/api/branch/" + branchRequest.getBranchNo());
        ResponseEntity<String> getResponseAfter = restTemplate
                .getForEntity("/api/branch/" + branchRequest.getBranchNo(), String.class);
        assertThat(getResponseAfter.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldUpdateAnExistingBranch() {
        // create data
        restTemplate.postForEntity("/api/branch", branchRequest, Void.class);
        // update and check existence
        branchRequest.setCity("Winnipeg");
        ResponseEntity<Void> updateResponse = restTemplate
                .exchange("/api/branch/" + branchRequest.getBranchNo(),
                        HttpMethod.PUT, httpRequest, Void.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        // check if the data is updated
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/branch/" + branchRequest.getBranchNo(), String.class);
        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        String cityInDatabase = documentContext.read("$.city");
        assertThat(cityInDatabase).isEqualTo(branchRequest.getCity());
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
