package com.dongli.dream_home.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class StaffTest {

    Staff staffNoPara = new Staff();
    Staff staffAllPara = new Staff(null, "Dongli", "Liu", "Manager", "M",
            LocalDate.of(1990, 6, 16), 50000, "Branch1",
            "123456789", "9876543210", "john.doe@example.com");

    @Test
    void testStaffCanBeCreatedWithNoAndAllParameters() {
        assertNotNull(staffNoPara);
        assertNotNull(staffAllPara);
    }

    @Test
    void testBasicFieldsCanBeSet() {
        assertThat(staffAllPara.getFName()).isEqualTo("Dongli");
        staffAllPara.setFName("George");
        assertThat(staffAllPara.getFName()).isEqualTo("George");

        assertThat(staffAllPara.getLName()).isEqualTo("Liu");
        staffAllPara.setLName("Smith");
        assertThat(staffAllPara.getLName()).isEqualTo("Smith");

        assertThat(staffAllPara.getPosition()).isEqualTo("Manager");
        staffAllPara.setPosition("Supervisor");
        assertThat(staffAllPara.getPosition()).isEqualTo("Supervisor");
    }

    @Test
    void testContactInformationFieldsCanBeSet() {
        assertThat(staffAllPara.getEmail()).isEqualTo("john.doe@example.com");
        staffAllPara.setEmail("george.smith@example.com");
        assertThat(staffAllPara.getEmail()).isEqualTo("george.smith@example.com");

        assertThat(staffAllPara.getTelephone()).isEqualTo("123456789");
        staffAllPara.setTelephone("987654321");
        assertThat(staffAllPara.getTelephone()).isEqualTo("987654321");

        assertThat(staffAllPara.getMobile()).isEqualTo("9876543210");
        staffAllPara.setMobile("1234567890");
        assertThat(staffAllPara.getMobile()).isEqualTo("1234567890");
    }

    @Test
    void testNumericFieldsCanBeSet() {
        assertThat(staffAllPara.getSalary()).isEqualTo(50000);
        staffAllPara.setSalary(60000);
        assertThat(staffAllPara.getSalary()).isEqualTo(60000);
    }

    @Test
    void testDateFieldsCanBeSet() {
        assertThat(staffAllPara.getDob()).isEqualTo(LocalDate.of(1990, 6, 16));
        staffAllPara.setDob(LocalDate.of(1995, 10, 20));
        assertThat(staffAllPara.getDob()).isEqualTo(LocalDate.of(1995, 10, 20));
    }
}
