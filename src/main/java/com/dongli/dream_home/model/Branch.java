package com.dongli.dream_home.model;

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
@Table(name = "dh_branch")
public class Branch {
    @Id
    @Column(name = "branchno")
    private String branchNo;
    private String street;
    private String city;
    @Column(name = "postcode")
    private String postCode;
}
