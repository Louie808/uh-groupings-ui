package edu.hawaii.its.groupings.type;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class TypeTest {

    private Type type;

    @BeforeEach
    public void setUp() {
        type = new Type();
    }

    @Test
    public void construction() {
        Assertions.assertNotNull(type);
    }

    @Test
    public void setters() {
        Assertions.assertNotNull(type);
        Assertions.assertNull(type.getId());
        Assertions.assertNull(type.getDescription());
        Assertions.assertNull(type.getVersion());

        type.setId(666);
        type.setDescription("The Beast");
        type.setVersion(9);
        MatcherAssert.assertThat(type.getId(), is(666));
        MatcherAssert.assertThat(type.getDescription(), is("The Beast"));
        MatcherAssert.assertThat(type.getVersion(), is(9));
    }

    @Test
    public void testToString() {
        MatcherAssert.assertThat(type.toString(), containsString("id=null, description=null"));

        type.setId(12345);
        MatcherAssert.assertThat(type.toString(), containsString("Type [id=12345,"));
    }
}
