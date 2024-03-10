package com.dongli.dream_home;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpClientErrorException;

import com.dongli.dream_home.dto.StaffRequest;
import com.dongli.dream_home.dto.StaffResponse;

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
		// Attempt to delete the staff member with ID "SW50" if it exists
		try {
			restTemplate.delete("/api/staff/SW50");
		} catch (HttpClientErrorException e) {
			// Ignore if the staff member does not exist
			if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
				throw e;
			}
		}
	}

	@AfterEach
	void tearDown() {
		// Do the same thing as setUp
		try {
			restTemplate.delete("/api/staff/SW50");
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
				throw e;
			}
		}
	}

	@Test
	@DirtiesContext
	// Commented since it will violate the unique constraints
	void shouldReturnAStaffResponseWhenStaffHired() {
		ResponseEntity<Void> createdResponse = restTemplate
				.postForEntity("/api/staff", staffRequest, Void.class);
		assertThat(createdResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	@DirtiesContext
	void shouldNotCreateADuplicatedStaff() {
		restTemplate.postForEntity("/api/staff", staffRequest, Void.class);
		ResponseEntity<Void> createdResponse2 = restTemplate
				.postForEntity("/api/staff", staffRequest, Void.class);
		assertThat(createdResponse2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	void shouldReturnAStaffIfIdMatches() {
		String staffNo = "SW50";
	}

	@Test
	void shouldReturnAllStaffsIfRequested() {
		ResponseEntity<String> response = restTemplate
				.getForEntity("/api/staff", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
