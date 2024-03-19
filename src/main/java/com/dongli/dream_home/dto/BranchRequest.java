package com.dongli.dream_home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchRequest {
    private String branchNo;
    private String street;
    private String city;
    private String postCode;
}
