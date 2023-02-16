package edu.hawaii.its.groupings.type;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class UserRoleTest {

    private UserRole userRole;

    @BeforeEach
    public void setUp() {
        userRole = new UserRole();
    }

    @Test
    public void construction() {
        Assertions.assertNotNull(userRole);
    }

    @Test
    public void setters() {
        Assertions.assertNotNull(userRole);

        Assertions.assertNull(userRole.getId());
        Assertions.assertNull(userRole.getAuthority());
        Assertions.assertNull(userRole.getVersion());

        userRole.setId(666);
        userRole.setAuthority("The Beast");
        userRole.setVersion(9);
        MatcherAssert.assertThat(userRole.getId(), is(666));
        MatcherAssert.assertThat(userRole.getAuthority(), is("The Beast"));
        MatcherAssert.assertThat(userRole.getVersion(), is(9));
    }

    @Test
    public void testToString() {
        String expected = "UserRole [id=null, version=null, authority=null]";
        MatcherAssert.assertThat(userRole.toString(), containsString(expected));

        userRole.setId(12345);
        MatcherAssert.assertThat(userRole.toString(), containsString("UserRole [id=12345,"));
    }
}
