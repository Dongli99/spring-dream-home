package com.dongli.dream_home.repository;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dongli.dream_home.model.Staff;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class StaffRepositoryTest {
    @Autowired
    private StaffRepository staffRepository;

    @Test
    void testAddStaff() {
        Staff staff = new Staff("SW50", "Dongli", "Liu", "Manager", "M",
                LocalDate.of(1990, 6, 16), 50000, "Branch1",
                "123456789", "9876543210", "john.doe@example.com");
        staffRepository.save(staff);
    }
}
