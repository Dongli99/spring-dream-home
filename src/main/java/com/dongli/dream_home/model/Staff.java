package com.dongli.dream_home.model;

import java.time.LocalDate;

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
    @Column(length = 50)
    private String staffno;
    private String fname;
    private String lname;
    private String position;
    private String sex;
    private LocalDate dob;
    private int salary;
    private String branchno;
    private String telephone;
    private String mobile;
    private String email;

    // Constructor that initializes the identifier field
    public Staff(String staffno) {
        this.staffno = staffno;
    }
}
