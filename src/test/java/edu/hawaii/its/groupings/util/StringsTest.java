package edu.hawaii.its.groupings.util;

import org.junit.jupiter.api.Assertions;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.instanceOf;


import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class StringsTest {

    @Test
    public void fill() {
        String s = Strings.fill('$', 6);
        MatcherAssert.assertThat(s, is("$$$$$$"));
    }

    @Test
    public void isNotEmpty() {
        Assertions.assertTrue(Strings.isNotEmpty("t"));
        Assertions.assertTrue(Strings.isNotEmpty("test"));
        Assertions.assertTrue(Strings.isNotEmpty(" test "));
        Assertions.assertFalse(Strings.isNotEmpty(""));
        Assertions.assertFalse(Strings.isNotEmpty(" "));
        Assertions.assertFalse(Strings.isNotEmpty(null));
    }

    @Test
    public void isEmpty() {
        Assertions.assertFalse(Strings.isEmpty("t"));
        Assertions.assertFalse(Strings.isEmpty("test"));
        Assertions.assertFalse(Strings.isEmpty(" test "));
        Assertions.assertTrue(Strings.isEmpty(""));
        Assertions.assertTrue(Strings.isEmpty(" "));
        Assertions.assertTrue(Strings.isEmpty(null));
    }

    @Test
    public void trunctate() {
        String s = "abcdefghijk";
        MatcherAssert.assertThat(Strings.truncate(s, 3), is("abc"));
        MatcherAssert.assertThat(Strings.truncate(s, 2), is("ab"));
        MatcherAssert.assertThat(Strings.truncate(s, 1), is("a"));
        MatcherAssert.assertThat(Strings.truncate(s, 0), is(""));
        MatcherAssert.assertThat(Strings.truncate(s, 11), is(s));
        MatcherAssert.assertThat(Strings.truncate(s, 12), is(s));

        Assertions.assertNull(Strings.truncate(null, 0));
        Assertions.assertNull(Strings.truncate(null, 1));

        // Note this result:
        try {
            Strings.truncate(s, -1);
            Assertions.fail("Should not reach here.");
        } catch (Exception e) {
            MatcherAssert.assertThat(e, instanceOf(IndexOutOfBoundsException.class));
        }
    }

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor<Strings> constructor = Strings.class.getDeclaredConstructor();
        Assertions.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
