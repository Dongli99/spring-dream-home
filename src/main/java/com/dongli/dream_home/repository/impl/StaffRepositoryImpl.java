package com.dongli.dream_home.repository.impl;

import org.springframework.stereotype.Repository;

import com.dongli.dream_home.model.Staff;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StaffRepositoryImpl {
    private final EntityManager entityManager;

    public void hireStaff(Staff staff) {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("Staff_hire_sp")
                .registerStoredProcedureParameter("p_staffno", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_fname", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_lname", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_position", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_sex", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_dob", java.sql.Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_salary", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_branchno", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_telephone", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_mobile", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN)
                .setParameter("p_staffno", staff.getStaffNo())
                .setParameter("p_fname", staff.getFName())
                .setParameter("p_lname", staff.getLName())
                .setParameter("p_position", staff.getPosition())
                .setParameter("p_sex", staff.getSex())
                .setParameter("p_dob", staff.getDob())
                .setParameter("p_salary", staff.getSalary())
                .setParameter("p_branchno", staff.getBranchNo())
                .setParameter("p_telephone", staff.getTelephone())
                .setParameter("p_mobile", staff.getMobile())
                .setParameter("p_email", staff.getEmail());

        storedProcedure.execute();
    }
}
