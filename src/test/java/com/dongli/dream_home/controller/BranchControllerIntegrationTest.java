package com.dongli.dream_home.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BranchControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldReturnTheAddressIfBranchExists() {

    }
}
