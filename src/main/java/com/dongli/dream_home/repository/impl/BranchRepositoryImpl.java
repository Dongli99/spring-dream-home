package com.dongli.dream_home.repository.impl;

import org.springframework.stereotype.Repository;

import com.dongli.dream_home.model.Branch;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BranchRepositoryImpl {
    private final EntityManager entityManager;

    public void newBranch(Branch branch) {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("new_branch_sp")
                .registerStoredProcedureParameter("p_branchno", getClass(), ParameterMode.IN)
                .registerStoredProcedureParameter("p_street", getClass(), ParameterMode.IN)
                .registerStoredProcedureParameter("p_city", getClass(), ParameterMode.IN)
                .registerStoredProcedureParameter("p_postcode", getClass(), ParameterMode.IN)
                .setParameter("p_branchno", branch.getBranchNo())
                .setParameter("p_street", branch.getStreet())
                .setParameter("p_city", branch.getCity())
                .setParameter("p_postcode", branch.getPostCode());

        storedProcedure.execute();
    }
}