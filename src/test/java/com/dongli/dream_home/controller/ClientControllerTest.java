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

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dongli.dream_home.dto.ClientRequest;
import com.dongli.dream_home.dto.ClientResponse;
import com.dongli.dream_home.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {
        private MockMvc mockMvc;

        private ObjectMapper mapper;

        private ClientResponse clientResponse;
        private ClientRequest clientRequest;

        @Mock
        private ClientService clientService;

        @InjectMocks
        private ClientController clientController;

        @BeforeEach
        void setup() {
                mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
                mapper = new ObjectMapper();
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
                clientResponse = ClientResponse.builder()
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
        }

        @Test
        void testGetClientById() throws Exception {
                when(clientService.getClientById(anyString())).thenReturn(clientResponse);
                mockMvc.perform(get("/api/client/" + clientRequest.getClientNo()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.city")
                                                .value(clientResponse.getCity()));
        }

        void testCreateNewClient() throws Exception {
                // Mock the behavior of addNewClient in the service
                when(clientService.addClient(any(ClientRequest.class))).thenReturn(clientResponse);
                // Perform a POST request to the /api/client endpoint
                mockMvc.perform(post("/api/client")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(clientRequest)))
                                .andExpect(status().isCreated())
                                .andExpect(header()
                                                .string("Location", "http://localhost/api/client/"
                                                                + clientRequest.getClientNo()));
        }

        @Test
        void testUpdateClient() throws Exception {
                doNothing().when(clientService).updateClient(anyString(), any(ClientRequest.class));
                clientRequest.setCity("London");
                mockMvc.perform(put("/api/client/" + clientRequest.getClientNo())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(clientRequest)))
                                .andExpect(status().isNoContent());
        }

        @Test
        void testDeleteClient() throws Exception {
                doNothing().when(clientService).deleteClient(anyString());
                mockMvc.perform(delete("/api/client/" + clientRequest.getClientNo()))
                                .andExpect(status().isNoContent());
        }
}
