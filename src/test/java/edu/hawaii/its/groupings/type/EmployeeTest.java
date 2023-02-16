package edu.hawaii.its.groupings.type;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

public class EmployeeTest {

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
    }

    @Test
    public void construction() {
        Assertions.assertNotNull(employee);
        Assertions.assertNull(employee.getUhUuid());

        employee = new Employee(123456789L);
        MatcherAssert.assertThat(employee.getUhUuid(), equalTo(123456789L));
    }

    @Test
    public void setters() {
        Assertions.assertNotNull(employee);
        Assertions.assertNull(employee.getUhUuid());
        Assertions.assertNotNull(employee.toString());

        employee.setUhUuid(12345678L);
        MatcherAssert.assertThat(employee.getUhUuid(), equalTo(12345678L));
        MatcherAssert.assertThat(employee.toString(), containsString("uhUuid=12345678"));
    }
}
