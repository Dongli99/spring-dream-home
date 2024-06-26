package com.dongli.dream_home.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dongli.dream_home.dto.ClientRequest;
import com.dongli.dream_home.dto.ClientResponse;
import com.dongli.dream_home.exception.InconsistentDataException;
import com.dongli.dream_home.model.Client;
import com.dongli.dream_home.repository.ClientRepository;
import com.dongli.dream_home.exception.EntityNotFoundException;

import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientResponse addClient(ClientRequest clientRequest) {
        // check existence before creation
        Client existedClient = findById(clientRequest.getClientNo());
        if (existedClient != null)
            throw new EntityExistsException(
                    "Client " + clientRequest.getClientNo() + " already exists");
        // save new client
        Client client = mapToClient(clientRequest);
        clientRepository.save(client);
        // check existence after creation
        Client savedClient = findById(client.getClientNo());
        if (savedClient == null)
            throw new EntityNotFoundException("Failed to save the client");
        // return response
        ClientResponse clientResponse = mapToResponse(savedClient);
        log.info("Client {} saved.", clientResponse.getClientNo());
        return clientResponse;
    }

    public ClientResponse getClientById(String clientNo) {
        Client client = findById(clientNo);
        // check existence before return
        if (client == null)
            throw new EntityNotFoundException("Client " + clientNo + "is not found.");
        // return client response
        ClientResponse clientResponse = mapToResponse(client);
        return clientResponse;
    }

    public void updateClient(String clientNo, ClientRequest clientRequest) {
        // check existence before update
        Client existedClient = findById(clientNo);
        if (existedClient == null)
            throw new EntityNotFoundException(
                    "Client " + clientNo + " already exists.");
        // check consistency
        if (!clientNo.equals(clientRequest.getClientNo()))
            throw new InconsistentDataException("Client No cannot be modified.");
        // update client
        Client clientToUpdate = mapToClient(clientRequest);
        clientRepository.save(clientToUpdate);
        log.info("Client {} is updated.", clientNo);
    }

    public void deleteClient(String clientNo) {
        Client client = findById(clientNo);
        if (client == null)
            throw new EntityNotFoundException("Client " + clientNo + " not exists.");
        clientRepository.delete(client);
        log.info("Client {} is updated.", client.getClientNo());
    }

    public List<ClientResponse> listAll() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(this::mapToResponse).toList();
    }

    private Client findById(String clientNo) {
        Optional<Client> optionalClient = clientRepository.findById(clientNo);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            return client;
        } else {
            return null;
        }
    }

    private Client mapToClient(ClientRequest clientRequest) {
        return Client.builder()
                .clientNo(clientRequest.getClientNo())
                .firstName(clientRequest.getFirstName())
                .lastName(clientRequest.getLastName())
                .city(clientRequest.getCity())
                .street(clientRequest.getStreet())
                .email(clientRequest.getEmail())
                .prefType(clientRequest.getPrefType())
                .maxRent(clientRequest.getMaxRent())
                .telephoneNo(clientRequest.getTelephoneNo())
                .build();
    }

    private ClientResponse mapToResponse(Client client) {
        return ClientResponse.builder()
                .clientNo(client.getClientNo())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .city(client.getCity())
                .street(client.getStreet())
                .email(client.getEmail())
                .prefType(client.getPrefType())
                .maxRent(client.getMaxRent())
                .telephoneNo(client.getTelephoneNo())
                .build();
    }

}
