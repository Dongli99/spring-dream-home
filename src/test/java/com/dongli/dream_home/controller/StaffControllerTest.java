package com.dongli.dream_home.controller;

import com.dongli.dream_home.dto.StaffRequest;
import com.dongli.dream_home.dto.StaffResponse;
import com.dongli.dream_home.service.StaffService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class StaffControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private StaffService staffService;

    @InjectMocks
    private StaffController staffController;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(staffController).build();
    }

    @Test
    void testCreateStaff() throws Exception {
        // Given
        StaffRequest staffRequest = new StaffRequest("SW50", "Dongli", "Liu", "Manager", "M",
                LocalDate.of(1990, 6, 16), 50000, "B003",
                "123456789", "9876543210", "dongli.liu@example.com");

        // Mock the behavior of StaffService to return a non-null StaffResponse
        StaffResponse staffResponse = new StaffResponse();
        staffResponse.setStaffNo("SW50"); // Set whatever values are appropriate
        when(staffService.createStaffUsingProcedure(any(StaffRequest.class))).thenReturn(staffResponse);

        // When
        mockMvc.perform(post("/api/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(staffRequest)))
                .andExpect(status().isCreated());
    }

    // Helper method to convert objects to JSON string
    private String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
