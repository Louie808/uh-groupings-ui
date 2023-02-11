package edu.hawaii.its.groupings.access;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;

public class RoleTest {

    @Test
    public void longName() {
        for (Role role : Role.values()) {
            MatcherAssert.assertThat(role.longName(), is("ROLE_" + role.name()));
        }
    }

    @Test
    public void find() {
        Role role = Role.find(Role.ADMIN.name());
        Assertions.assertNotNull(role);
        MatcherAssert.assertThat(role.name(), is(Role.ADMIN.name()));
        MatcherAssert.assertThat(role.longName(), is(Role.ADMIN.longName()));
        MatcherAssert.assertThat(role.toString(), is("ROLE_ADMIN"));
        role = Role.find("non-existent-role");
        Assertions.assertNull(role);
    }
}
