package com.dongli.dream_home.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dongli.dream_home.dto.AddressResponse;
import com.dongli.dream_home.service.BranchService;

@ExtendWith(MockitoExtension.class)
public class BranchControllerTest {

    private MockMvc mockMvc;

    private AddressResponse addressResponse;

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchController branchController;

    @BeforeEach
    void setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(branchController).build();
        addressResponse = AddressResponse.builder()
                .city("London")
                .street("56 Cover Drive")
                .postCode("NW10 6EU")
                .build();
    }

    @Test
    void testSearchAddressById() throws Exception {
        // Mock the behavior of AddressResponse
        when(branchService.findBranchAddressById(anyString())).thenReturn(addressResponse);
        mockMvc.perform(get("/api/branch/address/B002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postCode")
                        .value(addressResponse.getPostCode()));
    }

}
