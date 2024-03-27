package com.dongli.dream_home.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {
    private String clientNo;
    private String firstName;
    private String lastName;
    private String telephoneNo;
    private String street;
    private String city;
    private String email;
    private String prefType;
    private BigDecimal maxRent;
}
