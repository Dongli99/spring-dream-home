package com.dongli.dream_home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.dongli.dream_home.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, String> {
    @Procedure(name = "new_branch_sp")
    void newBranch(Branch branch);
}