package com.dongli.dream_home.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongli.dream_home.model.Client;

public interface ClientRepository extends JpaRepository<Client, String> {
}