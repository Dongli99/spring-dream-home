package com.dongli.dream_home.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dh_staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String staffNo;
    @Column
    private String fName;
    @Column
    private String lName;
    @Column
    private String position;
    @Column
    private String sex;
    @Column
    private LocalDate dob;
    @Column
    private int salary;
    @Column
    private String branchNo;
    @Column
    private String telephone;
    @Column
    private String mobile;
    @Column
    private String email;
}
