package com.dongli.dream_home;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.dongli.dream_home.dto.StaffRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DreamHomeApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	TestRestTemplate restTemplate;

	@Bean
	TestRestTemplate testRestTemplate() {
		return new TestRestTemplate();
	}

	@Test
	void restTemplateShouldWork() {
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://www.google.com", String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	// @Test
	// @DirtiesContext
	// // Commented since it will violate the unique constraints
	// void shouldReturnAStaffResponseWhenStaffHired() {
	// StaffRequest staffRequest = new StaffRequest("SW50", "Dongli", "Liu",
	// "Manager", "M",
	// LocalDate.of(1990, 6, 16), 50000, "B003",
	// "123456789", "9876543210", "john.doe@example.com");
	// ResponseEntity<Void> createdResponse =
	// restTemplate.postForEntity("/api/staff", staffRequest, Void.class);
	// assertThat(createdResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	// }

	// @Test
	// @DirtiesContext
	// void shouldNotCreateADuplicatedStaff() {
	// StaffRequest staffRequest = new StaffRequest("SW50", "Dongli", "Liu",
	// "Manager", "M",
	// LocalDate.of(1990, 6, 16), 50000, "B003",
	// "123456789", "9876543210", "john.doe@example.com");
	// ResponseEntity<Void> createdResponse =
	// restTemplate.postForEntity("/api/staff", staffRequest, Void.class);
	// assertThat(createdResponse.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	// }

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
