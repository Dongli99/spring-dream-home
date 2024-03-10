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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class StaffControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private StaffRequest staffRequest;
    private StaffResponse staffResponse1;
    private List<StaffResponse> expectedStaffResponses;

    @Mock
    private StaffService staffService;

    @InjectMocks
    private StaffController staffController;

    @BeforeEach
    void setup() {
        // Enable LocalDate Json Serialization
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(staffController).build();

        // setup data
        staffRequest = new StaffRequest("SW50", "Dongli", "Liu", "Manager", "M",
                LocalDate.of(1990, 6, 16), 50000, "B003",
                "123456789", "9876543210", "dongli.liu@example.com");
        staffResponse1 = new StaffResponse("SW50", "Dongli", "Liu", "Manager", "M",
                LocalDate.of(1990, 6, 16), 50000, "B003",
                "123456789", "9876543210", "dongli.liu@example.com");
        StaffResponse staffResponse2 = new StaffResponse("SW51", "John", "Smith", "Manager", "M",
                LocalDate.of(1990, 6, 16), 50000, "B005",
                "123456789", "9876543210", "join.smith@example.com");
        expectedStaffResponses = new ArrayList<>();
        expectedStaffResponses.add(staffResponse1);
        expectedStaffResponses.add(staffResponse2);
    }

    @Test
    void testCreateStaff() throws Exception {
        // Mock the behavior of StaffService to return a non-null StaffResponse
        StaffResponse staffResponse = new StaffResponse();
        staffResponse.setStaffNo("SW50"); // Set whatever values are appropriate
        staffResponse.setBranchNo("B003");
        when(staffService.createStaffUsingProcedure(any(StaffRequest.class))).thenReturn(staffResponse);

        // When
        mockMvc.perform(post("/api/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(staffRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/staff/SW50"));
    }

    @Test
    void testListAllStaffs() throws Exception {
        // Mock the behavior of StaffService
        when(staffService.getAllStaffs()).thenReturn(expectedStaffResponses);

        // When
        mockMvc.perform(get("/api/staff"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].staffNo")
                        .value(expectedStaffResponses.get(0).getStaffNo()))
                .andExpect(jsonPath("$[1]").exists())
                .andExpect(jsonPath("$[2]").doesNotExist());
    }

    @Test
    void testFindStaffById() throws Exception {
        // Mock the behavior of StaffService
        when(staffService.findById(any(String.class))).thenReturn(staffResponse1);
        // When
        mockMvc.perform(get("/api/staff/" + staffResponse1.getStaffNo()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fname")
                        .value(staffResponse1.getFName()));
    }

    @Test
    void testDeleteStaffById() throws Exception {
        // Mock the behavior of StaffService
        doNothing().when(staffService).deleteById(any(String.class));

        // When
        mockMvc.perform(delete("/api/staff/" + staffResponse1.getStaffNo()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateStaff() throws Exception {
        // Mock the behavior of StaffService
        doNothing().when(staffService).updateById(any(String.class), any(StaffRequest.class));

        // When
        mockMvc.perform(put("/api/staff/" + staffResponse1.getStaffNo())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(staffRequest)))
                .andExpect(status().isNoContent());
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
