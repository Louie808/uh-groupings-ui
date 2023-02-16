package edu.hawaii.its.groupings.type;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class OwnerTest {

    private Owner owner;

    @BeforeEach
    public void setUp() {
        owner = new Owner();
    }

    @Test
    public void construction() {
        owner = new Owner("James T Kirk");
        MatcherAssert.assertThat(owner.getPrivilegeName(), is("James T Kirk"));
    }

    @Test
    public void name() {
        Assertions.assertNull(owner.getName());
        owner.setName("frank");
        MatcherAssert.assertThat(owner.getName(), is("frank"));
    }

    @Test
    public void privilegeName() {
        Assertions.assertNull(owner.getPrivilegeName());
        owner.setPrivilegeName("frd");
        MatcherAssert.assertThat(owner.getPrivilegeName(), is("frd"));
    }

    @Test
    public void uhUuid() {
        Assertions.assertNull(owner.getUhUuid());
        owner.setUhUuid("uhUuid");
        MatcherAssert.assertThat(owner.getUhUuid(), is("uhUuid"));
    }

    @Test
    public void uid() {
        Assertions.assertNull(owner.getUid());
        owner.setUid("uid");
        MatcherAssert.assertThat(owner.getUid(), is("uid"));
    }

    @Test
    public void testToString() {
        owner.setPrivilegeName("duke");
        MatcherAssert.assertThat(owner.toString(), containsString("Owner [privilegeName=duke,"));
    }
}
