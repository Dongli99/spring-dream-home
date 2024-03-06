package com.dongli.dream_home.model;

import java.time.LocalDate;

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
    private String staffNo;
    private String fName;
    private String lName;
    private String position;
    private String sex;
    private LocalDate dob;
    private int salary;
    private String branchNo;
    private String telephone;
    private String mobile;
    private String email;
}
