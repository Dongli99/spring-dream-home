package com.dongli.dream_home.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(name = "dh_staff")
public class Staff {
    @Id
    @Column(name = "staffno")
    private String staffNo;

    @Column(name = "fname")
    private String fName;

    @Column(name = "lname")
    private String lName;

    private String position;
    private String sex;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private int salary;

    @Column(name = "branchno")
    private String branchNo;
    private String telephone;
    private String mobile;
    private String email;
}
