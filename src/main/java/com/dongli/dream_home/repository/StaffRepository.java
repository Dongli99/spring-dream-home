package com.dongli.dream_home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.dongli.dream_home.model.Staff;

public interface StaffRepository extends JpaRepository<Staff, String> {
    @Procedure(name = "Staff_hire_sp")
    Staff hireStaff(Staff staff);
}