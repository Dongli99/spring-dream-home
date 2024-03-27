package com.dongli.dream_home.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dh_client")
public class Client {
    @Id
    @Column(name = "clientno")
    private String clientNo;
    @Column(name = "fname")
    private String firstName;
    @Column(name = "lname")
    private String lastName;
    @Column(name = "telno", columnDefinition = "char(20)")
    private String telephoneNo;
    private String street;
    private String city;
    private String email;
    @Column(name = "preftype")
    private String prefType;
    @Column(name = "maxrent")
    private BigDecimal maxRent;
}
