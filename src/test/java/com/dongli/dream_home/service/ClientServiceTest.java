package com.dongli.dream_home.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dongli.dream_home.dto.ClientRequest;
import com.dongli.dream_home.dto.ClientResponse;
import com.dongli.dream_home.model.Client;
import com.dongli.dream_home.repository.ClientRepository;

import jakarta.persistence.EntityExistsException;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    private Client client;
    private ClientRequest clientRequest;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        client = Client.builder()
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
    }

    @Test
    void testGetClientById() {
        when(clientRepository.findById(anyString())).thenReturn(Optional.of(client));
        ClientResponse clientResponse = clientService.getClientById(clientRequest.getClientNo());
        assertNotNull(clientResponse);
        assertThat(clientResponse.getCity()).isEqualTo(client.getCity());
    }

    @Test
    void testAddClient() {
        when(clientRepository.findById(client.getClientNo()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        ClientResponse response = clientService.addClient(clientRequest);
        assertNotNull(response);
        assertEquals(clientRequest.getClientNo(), response.getClientNo());
        assertEquals(clientRequest.getStreet(), response.getStreet());
    }

    @Test
    void testAddNewClientWhenTheClientExists() {
        ClientRequest conflictClientRequest = new ClientRequest();
        conflictClientRequest.setClientNo(clientRequest.getClientNo());
        conflictClientRequest.setCity("London");
        conflictClientRequest.setStreet("56 Cover Drive");
        when(clientRepository.findById(clientRequest.getClientNo())).thenReturn(Optional.of(client));
        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> {
            clientService.addClient(conflictClientRequest);
        });
    }

    @Test
    void testUpdateById() {
        String clientNo = clientRequest.getClientNo();
        when(clientRepository.findById(clientNo)).thenReturn(Optional.of(client));
        clientService.updateClient(clientNo, clientRequest);
        verify(clientRepository).findById(clientNo);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testDeleteClient() {
        when(clientRepository.findById(clientRequest.getClientNo())).thenReturn(Optional.of(client));
        doNothing().when(clientRepository).delete(client);
        clientService.deleteClient(clientRequest.getClientNo());
        verify(clientRepository).findById(clientRequest.getClientNo());
        verify(clientRepository).delete(client);
    }

}
