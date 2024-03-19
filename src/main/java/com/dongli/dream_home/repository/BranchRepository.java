package com.dongli.dream_home.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dongli.dream_home.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, String> {
    // @Procedure(name = "Staff_hire_sp")
    // Staff hireStaff(Staff staff);
}