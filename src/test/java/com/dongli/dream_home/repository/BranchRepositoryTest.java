package com.dongli.dream_home.repository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dongli.dream_home.model.Branch;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BranchRepositoryTest {
    @Autowired
    private BranchRepository branchRepository;

    @MockBean
    private BranchRepository mockBranchRepository;

    @Test
    void testNewBranchProcedure() {
        Branch branch = Branch.builder()
                .branchNo("B002")
                .city("London")
                .street("56 Cover Drive")
                .postCode("NW10 6EU")
                .build();
        branchRepository.newBranch(branch);
        // verifies that the method was called once.
        verify(mockBranchRepository, times(1)).newBranch(branch);
    }
}
