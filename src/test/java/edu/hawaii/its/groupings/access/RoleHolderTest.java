package edu.hawaii.its.groupings.access;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
public class RoleHolderTest {

    @Test
    public void basics() {
        RoleHolder roleHolder = new RoleHolder();
        MatcherAssert.assertThat(roleHolder.size(), is(0));
        roleHolder.add(Role.ANONYMOUS);
        MatcherAssert.assertThat(roleHolder.size(), is(1));
        roleHolder.add(Role.UH);
        MatcherAssert.assertThat(roleHolder.size(), is(2));
        roleHolder.add(Role.EMPLOYEE);
        MatcherAssert.assertThat(roleHolder.size(), is(3));
        roleHolder.add(Role.OWNER);
        MatcherAssert.assertThat(roleHolder.size(), is(4));
        roleHolder.add(Role.ADMIN);
        MatcherAssert.assertThat(roleHolder.size(), is(5));

        MatcherAssert.assertThat(roleHolder.toString(), containsString("ROLE_ANONYMOUS"));
        MatcherAssert.assertThat(roleHolder.toString(), containsString("ROLE_UH"));
        MatcherAssert.assertThat(roleHolder.toString(), containsString("ROLE_EMPLOYEE"));
        MatcherAssert.assertThat(roleHolder.toString(), containsString("ROLE_OWNER"));
        MatcherAssert.assertThat(roleHolder.toString(), containsString("ROLE_ADMIN"));
    }
}
