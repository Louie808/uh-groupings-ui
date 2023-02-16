package edu.hawaii.its.groupings.type;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

public class FeedbackTest {

    private Feedback feedback;

    @BeforeEach
    public void setUp() {
        feedback = new Feedback();
    }

    @Test
    public void setters() {
        Assertions.assertNotNull(feedback);
        Assertions.assertNull(feedback.getName());
        Assertions.assertNull(feedback.getEmail());
        Assertions.assertNull(feedback.getType());
        Assertions.assertNull(feedback.getMessage());
        Assertions.assertNull(feedback.getExceptionMessage());

        feedback.setName("Test User");
        feedback.setEmail("test@hawaii.edu");
        feedback.setType("Problem");
        feedback.setMessage("Test Message");
        feedback.setExceptionMessage("Exception Message");

        MatcherAssert.assertThat(feedback.getName(), equalTo("Test User"));
        MatcherAssert.assertThat(feedback.getEmail(), equalTo("test@hawaii.edu"));
        MatcherAssert.assertThat(feedback.getType(), equalTo("Problem"));
        MatcherAssert.assertThat(feedback.getMessage(), equalTo("Test Message"));
        MatcherAssert.assertThat(feedback.getExceptionMessage(), equalTo("Exception Message"));

        MatcherAssert.assertThat(feedback.toString(), containsString("email=test@hawaii.edu"));
        MatcherAssert.assertThat(feedback.toString(), containsString("exceptionMessage=Exception Message"));
    }

    @Test
    public void exceptionConstruction() {
        feedback = new Feedback("Test Exception Stack Trace");

        Assertions.assertNotNull(feedback);
        Assertions.assertNull(feedback.getName());
        Assertions.assertNull(feedback.getEmail());
        Assertions.assertNull(feedback.getType());
        Assertions.assertNull(feedback.getMessage());
        MatcherAssert.assertThat(feedback.getExceptionMessage(), equalTo("Test Exception Stack Trace"));

        MatcherAssert.assertThat(feedback.toString(), containsString("exceptionMessage=Test Exception Stack Trace"));
    }

}