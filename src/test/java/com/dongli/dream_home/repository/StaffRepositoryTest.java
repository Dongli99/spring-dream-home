package com.dongli.dream_home.repository;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dongli.dream_home.model.Staff;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class StaffRepositoryTest {
    @Autowired
    private StaffRepository staffRepository;

    @MockBean
    private StaffRepository mockStaffRepository;

    @Test
    void testHireStaffProcedure() {
        Staff staff = new Staff("SW50", "Dongli", "Liu", "Manager", "M",
                LocalDate.of(1990, 6, 16), 50000, "B007",
                "123456789", "9876543210", "john.doe@example.com");
        staffRepository.hireStaff(staff);
        // verifies that the method was called once.
        verify(mockStaffRepository, times(1)).hireStaff(staff);
    }
}
