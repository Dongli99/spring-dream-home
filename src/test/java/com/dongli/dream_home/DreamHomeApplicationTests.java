package com.dongli.dream_home;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpClientErrorException;

import com.dongli.dream_home.dto.StaffRequest;
import com.dongli.dream_home.dto.StaffResponse;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DreamHomeApplicationTests {

	private StaffRequest staffRequest;

	@Test
	void contextLoads() {
	}

	@Autowired
	TestRestTemplate restTemplate;

	@Bean
	TestRestTemplate testRestTemplate() {
		return new TestRestTemplate();
	}

	@BeforeEach
	void setUp() {
		// setup data
		staffRequest = new StaffRequest("SW50", "Dongli", "Liu",
				"Manager", "M",
				LocalDate.of(1990, 6, 16), 50000, "B003",
				"123456789", "9876543210", "john.doe@example.com");
		// cleanup database
		cleanupDatabase();
	}

	@AfterEach
	void tearDown() {
		cleanupDatabase();
	}

	@Test
	@DirtiesContext
	void shouldReturnAStaffResponseWhenStaffHired() {
		ResponseEntity<Void> createdResponse = restTemplate
				.postForEntity("/api/staff", staffRequest, Void.class);
		assertThat(createdResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		// check if staff exists in database
		ResponseEntity<String> getResponse = restTemplate
				.getForEntity("/api/staff/" + staffRequest.getStaffNo(),
						String.class);
		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		String id = documentContext.read("$.staffNo");
		assertThat(id).isEqualTo(staffRequest.getStaffNo());
	}

	@Test
	@DirtiesContext
	void shouldNotCreateADuplicatedStaff() {
		restTemplate.postForEntity("/api/staff", staffRequest, Void.class);
		ResponseEntity<Void> createdResponse = restTemplate
				.postForEntity("/api/staff", staffRequest, Void.class);
		assertThat(createdResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	@DirtiesContext
	void shouldNotCreateAStaffWithInvalidBranchNo() {
		staffRequest.setBranchNo("XXXX01");
		ResponseEntity<Void> createdResponse = restTemplate
				.postForEntity("/api/staff", staffRequest, Void.class);
		assertThat(createdResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
	}

	@Test
	void shouldReturnAStaffIfExists() {
		restTemplate.postForEntity("/api/staff", staffRequest, Void.class);
		ResponseEntity<String> getResponse = restTemplate
				.getForEntity("/api/staff/" + staffRequest.getStaffNo(),
						String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		// check if the staff returned is the same one
		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		String id = documentContext.read("$.staffNo");
		assertThat(id).isEqualTo(staffRequest.getStaffNo());
	}

	@Test
	void shouldNotReturnAStaffIfNotExists() {
		ResponseEntity<String> getResponse = restTemplate
				.getForEntity("/api/staff/" + staffRequest.getStaffNo(),
						String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldReturnAllStaffsIfRequested() {
		restTemplate.postForEntity("/api/staff", staffRequest, Void.class);
		ResponseEntity<String> response = restTemplate
				.getForEntity("/api/staff", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		// test size of response data
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		List<StaffResponse> staffResponses = documentContext.read("$");
		assertThat(staffResponses.size()).isGreaterThanOrEqualTo(1);
	}

	@Test
	void shouldUpdateAnExistingStaff() {
		restTemplate.postForEntity("/api/staff", staffRequest, Void.class);
		staffRequest.setSalary(1000); // update data
		HttpEntity<StaffRequest> httpRequest = new HttpEntity<>(staffRequest);
		ResponseEntity<Void> updateResponse = restTemplate
				.exchange("/api/staff/" + staffRequest.getStaffNo(),
						HttpMethod.PUT, httpRequest, Void.class);
		assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		// test if the data is updated in database
		ResponseEntity<String> getResponse = restTemplate
				.getForEntity("/api/staff/" + staffRequest.getStaffNo(),
						String.class);
		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		int salary = documentContext.read("$.salary");
		assertThat(salary).as("Salary should be updated").isEqualTo(1000);
	}

	@Test
	void shouldNotUpdateAnStaffIfBranchNoInvalid() {
		restTemplate.postForEntity("/api/staff", staffRequest, Void.class);
		staffRequest.setBranchNo("XXXX01"); // update data
		HttpEntity<StaffRequest> httpRequest = new HttpEntity<>(staffRequest);
		ResponseEntity<Void> updateResponse = restTemplate
				.exchange("/api/staff/" + staffRequest.getStaffNo(),
						HttpMethod.PUT, httpRequest, Void.class);
		assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
		// Ensure the data is NOT updated in database
		ResponseEntity<String> getResponse = restTemplate
				.getForEntity("/api/staff/" + staffRequest.getStaffNo(),
						String.class);
		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		String branchNo = documentContext.read("$.branchNo");
		assertThat(branchNo).as("Invalid BranchNo should not be updated").isEqualTo("B003");
	}

	@Test
	void shouldDeleteStaffIfExists() {
		restTemplate.postForEntity("/api/staff", staffRequest, Void.class);
		restTemplate.delete("/api/staff/" + staffRequest.getStaffNo());
		// Check if the staff member is deleted
		ResponseEntity<String> getResponse = restTemplate
				.getForEntity("/api/staff/" + staffRequest.getStaffNo(), String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldNotDeleteStaffIfNotExists() {
		ResponseEntity<Void> deleteResponse = restTemplate
				.exchange("/api/staff/SW50", HttpMethod.DELETE,
						null, Void.class);
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	// A helper method cleanup the database for testing environment
	// It enabled flexibility in performing testings to persistent Oracle Database
	private void cleanupDatabase() {
		try {
			restTemplate.delete("/api/staff/SW50");
		} catch (HttpClientErrorException e) {
			// Ignore if the staff member does not exist
			if (e.getStatusCode() != HttpStatus.NOT_FOUND)
				throw e;
		}
	}
}
