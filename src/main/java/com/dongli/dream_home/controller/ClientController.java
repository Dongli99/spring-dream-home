package com.dongli.dream_home.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.dongli.dream_home.dto.ClientRequest;
import com.dongli.dream_home.dto.ClientResponse;
import com.dongli.dream_home.service.ClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<Void> createNewClient(
            @RequestBody ClientRequest clientRequest,
            UriComponentsBuilder ucb) {
        ClientResponse clientResponse = clientService.addClient(clientRequest);
        URI locationOfNewClient = ucb
                .path("/api/client/{id}")
                .buildAndExpand(clientResponse.getClientNo())
                .toUri();
        return ResponseEntity.created(locationOfNewClient).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ClientResponse> getClientById(
            @PathVariable("id") String clientNo) {
        ClientResponse clientResponse = clientService.getClientById(clientNo);
        return ResponseEntity.ok(clientResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateClient(
            @PathVariable("id") String clientNo,
            @RequestBody ClientRequest clientRequest) {
        clientService.updateClient(clientNo, clientRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") String clientNo) {
        clientService.deleteClient(clientNo);
        return ResponseEntity.noContent().build();
    }

}
