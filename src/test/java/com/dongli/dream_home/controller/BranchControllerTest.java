package com.dongli.dream_home.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dongli.dream_home.dto.AddressResponse;
import com.dongli.dream_home.dto.BranchRequest;
import com.dongli.dream_home.dto.BranchResponse;
import com.dongli.dream_home.service.BranchService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class BranchControllerTest {

        private MockMvc mockMvc;

        private ObjectMapper mapper;

        private AddressResponse addressResponse;
        private BranchResponse branchResponse;
        private BranchRequest branchRequest;

        @Mock
        private BranchService branchService;

        @InjectMocks
        private BranchController branchController;

        @BeforeEach
        void setup() {
                mockMvc = MockMvcBuilders.standaloneSetup(branchController).build();
                mapper = new ObjectMapper();
                addressResponse = AddressResponse.builder()
                                .city("London")
                                .street("56 Cover Drive")
                                .postCode("NW10 6EU")
                                .build();
                branchResponse = BranchResponse.builder()
                                .branchNo("B002")
                                .city("London")
                                .street("56 Cover Drive")
                                .postCode("NW10 6EU")
                                .build();
                branchRequest = BranchRequest.builder()
                                .branchNo("B002")
                                .street("56 Cover Drive")
                                .city("London")
                                .postCode("NW10 6EU")
                                .build();
        }

        @Test
        void testSearchBranchById() throws Exception {
                when(branchService.findBranchById(anyString())).thenReturn(branchResponse);
                mockMvc.perform(get("/api/branch/" + branchRequest.getBranchNo()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.postCode")
                                                .value(addressResponse.getPostCode()));
        }

        @Test
        void testSearchAddressById() throws Exception {
                // Mock the behavior of AddressResponse
                when(branchService.findBranchAddressById(anyString())).thenReturn(addressResponse);
                mockMvc.perform(get("/api/branch/address/" + branchRequest.getBranchNo()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.postCode")
                                                .value(addressResponse.getPostCode()));
        }

        @Test
        @SuppressWarnings("null")
        void testOpenNewBranch() throws Exception {
                // Mock the behavior of addNewBranch in the service
                when(branchService.addNewBranch(any(BranchRequest.class))).thenReturn(branchResponse);
                // Perform a POST request to the /api/branch endpoint
                mockMvc.perform(post("/api/branch")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(branchRequest)))
                                .andExpect(status().isCreated())
                                .andExpect(header().string("Location", "http://localhost/api/branch/B002"));
        }

        @Test
        void testUpdateBranch() throws Exception {
                doNothing().when(branchService).updateById(anyString(), any(BranchRequest.class));
                branchRequest.setPostCode("NW10 1EU");
                mockMvc.perform(put("/api/branch/" + branchRequest.getBranchNo())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(branchRequest)))
                                .andExpect(status().isNoContent());
        }

        @Test
        void testDeleteBranch() throws Exception {
                doNothing().when(branchService).deleteBranchById(anyString());
                mockMvc.perform(delete("/api/branch/" + branchRequest.getBranchNo()))
                                .andExpect(status().isNoContent());
        }
}
