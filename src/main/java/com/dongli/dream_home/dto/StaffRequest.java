package com.dongli.dream_home.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffRequest {
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
