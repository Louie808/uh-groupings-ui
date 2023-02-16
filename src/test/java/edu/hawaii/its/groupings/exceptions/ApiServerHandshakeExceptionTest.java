package edu.hawaii.its.groupings.exceptions;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;

public class ApiServerHandshakeExceptionTest {

    @Test
    public void construction() {
        InitializationException ex = new ApiServerHandshakeException("fail");
        Assertions.assertNotNull(ex);
        MatcherAssert.assertThat(ex.getMessage(), equalTo("fail"));

        ex = new ApiServerHandshakeException("stop", new Throwable("me"));
        Assertions.assertNotNull(ex);
        MatcherAssert.assertThat(ex.getCause(), instanceOf(Throwable.class));
        String expected = "stop; nested exception is java.lang.Throwable: me";
        MatcherAssert.assertThat(ex.getMessage(), equalTo(expected));
        MatcherAssert.assertThat(ex.getLocalizedMessage(), equalTo(expected));
    }
}