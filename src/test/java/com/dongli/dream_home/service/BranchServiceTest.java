package com.dongli.dream_home.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.dongli.dream_home.dto.AddressResponse;
import com.dongli.dream_home.dto.BranchRequest;
import com.dongli.dream_home.dto.BranchResponse;
import com.dongli.dream_home.exception.EntityNotFoundException;
import com.dongli.dream_home.model.Branch;
import com.dongli.dream_home.repository.BranchRepository;

import jakarta.persistence.EntityExistsException;

@ExtendWith(MockitoExtension.class)
public class BranchServiceTest {

    private Branch branch;
    private BranchRequest branchRequest;

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchService branchService;

    @BeforeEach
    void setUp() {
        branch = Branch.builder()
                .branchNo("B002")
                .city("London")
                .street("56 Cover Drive")
                .postCode("NW10 6EU")
                .build();
        branchRequest = BranchRequest.builder()
                .branchNo("B002")
                .city("London")
                .street("56 Cover Drive")
                .postCode("NW10 6EU")
                .build();
        ReflectionTestUtils.setField(branchService, "activeProfile", "h2");
    }

    @Test
    void testFindBranchById() {
        when(branchRepository.findById(anyString())).thenReturn(Optional.of(branch));
        BranchResponse branchResponse = branchService.findBranchById("123");
        assertNotNull(branchResponse);
        assertThat(branchResponse.getCity()).isEqualTo(branch.getCity());
    }

    @Test
    void testFindBranchAddressById() {
        when(branchRepository.findById(anyString())).thenReturn(Optional.of(branch));
        AddressResponse addressResponse = branchService.findBranchAddressById("123");
        assertNotNull(addressResponse);
        assertThat(addressResponse.getStreet()).isEqualTo(branch.getStreet());
    }

    @Test
    void testAddNewBranch() {
        when(branchRepository.findById("B002"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(branch));
        when(branchRepository.save(any(Branch.class))).thenReturn(branch);
        BranchResponse response = branchService.addNewBranch(branchRequest);
        assertNotNull(response);
        assertEquals("B002", response.getBranchNo());
        assertEquals("London", response.getCity());
        assertEquals("56 Cover Drive", response.getStreet());
        assertEquals("NW10 6EU", response.getPostCode());
    }

    @Test
    void testAddNewBranchWhenTheBranchExists() {
        BranchRequest branchRequest = new BranchRequest();
        branchRequest.setBranchNo("B002");
        branchRequest.setCity("London");
        branchRequest.setStreet("56 Cover Drive");
        branchRequest.setPostCode("NW10 6EU");
        when(branchRepository.findById("B002")).thenReturn(Optional.of(branch));
        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> {
            branchService.addNewBranch(branchRequest);
        });
        assertEquals("Branch B002 already exists.", exception.getMessage());
    }

    @Test
    void testUpdateById() {
        String branchNo = branchRequest.getBranchNo();
        when(branchRepository.findById(branchNo)).thenReturn(Optional.of(branch));
        branchService.updateById(branchNo, branchRequest);
        verify(branchRepository).findById(branchNo);
        verify(branchRepository, times(1)).save(branch);
    }

    @Test
    void testDeleteBranchByIdWhenIdExists() {
        String branchNo = branchRequest.getBranchNo();
        when(branchRepository.findById(branchNo)).thenReturn(Optional.of(branch));
        branchService.deleteBranchById(branchNo);
        verify(branchRepository).findById(branchNo);
        verify(branchRepository, times(1)).deleteById(branchNo);
    }

    @Test
    void testDeleteBranchByIdWhenIdNotExists() {
        String branchNo = branchRequest.getBranchNo();
        when(branchRepository.findById(branchNo))
                .thenReturn(Optional.of(branch))
                .thenReturn(Optional.empty());
        branchService.updateById(branchNo, branchRequest);
        verify(branchRepository).findById(branchNo);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            branchService.deleteBranchById(branchNo);
        });
        assertEquals("Branch " + branchNo + " is not found.", exception.getMessage());
    }
}
