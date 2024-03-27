package com.dongli.dream_home.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

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

import com.dongli.dream_home.dto.ClientRequest;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    private ClientRequest clientRequest;

    private HttpEntity<ClientRequest> httpRequest;

    @BeforeEach
    void setUp() {
        // setup request data
        clientRequest = ClientRequest.builder()
                .clientNo("T123")
                .firstName("John")
                .lastName("Doe")
                .telephoneNo("123 6789")
                .street("123 Main St")
                .city("New York")
                .email("john.doe@example.com")
                .prefType("Type")
                .maxRent(BigDecimal.valueOf(1000))
                .build();
        cleanupDatabase();
        httpRequest = new HttpEntity<>(clientRequest);
    }

    @AfterEach
    void tearDown() {
        cleanupDatabase();
    }

    @Test
    void shouldCreateAClientIfNoConflicts() {
        ResponseEntity<Void> createResponseEntity = restTemplate
                .postForEntity("/api/client", clientRequest, Void.class);
        assertThat(createResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        // check existence of the client
        ResponseEntity<String> getResponseEntity = restTemplate
                .getForEntity("/api/client/" + clientRequest.getClientNo(), String.class);
        assertThat(getResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // check consistence of data
        DocumentContext documentContext = JsonPath.parse(getResponseEntity.getBody());
        String cityInDatabase = documentContext.read("$.city");
        assertThat(cityInDatabase).isEqualTo(clientRequest.getCity());
    }

    @Test
    void shouldNotCreateADuplicatedClient() {
        restTemplate.postForEntity("/api/client", clientRequest, Void.class); // 1st creation
        ResponseEntity<Void> createResponseEntity = restTemplate // 2nd creation
                .postForEntity("/api/client", clientRequest, Void.class);
        assertThat(createResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldUpdateAnExistingClient() {
        // create data
        restTemplate.postForEntity("/api/client", clientRequest, Void.class);
        // update and check existence
        clientRequest.setCity("Winnipeg");
        ResponseEntity<Void> updateResponse = restTemplate
                .exchange("/api/client/" + clientRequest.getClientNo(),
                        HttpMethod.PUT, httpRequest, Void.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        // check if the data is updated
        ResponseEntity<String> getResponse = restTemplate
                .getForEntity("/api/client/" + clientRequest.getClientNo(), String.class);
        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        String cityInDatabase = documentContext.read("$.city");
        assertThat(cityInDatabase).isEqualTo(clientRequest.getCity());
    }

    @Test
    void shouldNotUpdateANotExistingClient() {
        // update and check existence
        clientRequest.setCity("Winnipeg");
        ResponseEntity<Void> updateResponse = restTemplate
                .exchange("/api/client/" + clientRequest.getClientNo(),
                        HttpMethod.PUT, httpRequest, Void.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private void cleanupDatabase() {
        try {
            restTemplate.delete("/api/client/T123");
        } catch (HttpClientErrorException e) {
            // Ignore if the staff member does not exist
            if (e.getStatusCode() != HttpStatus.NOT_FOUND)
                throw e;
        }
    }
}
