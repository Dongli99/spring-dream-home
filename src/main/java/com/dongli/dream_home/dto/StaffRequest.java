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
}
