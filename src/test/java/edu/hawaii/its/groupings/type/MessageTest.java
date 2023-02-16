package edu.hawaii.its.groupings.type;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

public class MessageTest {

    private Message message;

    @BeforeEach
    public void setUp() {
        message = new Message();
    }

    @Test
    public void construction() {
        Assertions.assertNotNull(message);
    }

    @Test
    public void setters() {
        Assertions.assertNotNull(message);
        Assertions.assertNull(message.getId());
        Assertions.assertNull(message.getEnabled());
        Assertions.assertNull(message.getText());
        Assertions.assertNull(message.getTypeId());

        message.setId(666);
        MatcherAssert.assertThat(message.getId(), equalTo(666));
    }

    @Test
    public void testToString() {
        String expected = "Message [id=null, typeId=null, enabled=null, text=null]";
        MatcherAssert.assertThat(message.toString(), containsString(expected));

        message.setId(12345);
        MatcherAssert.assertThat(message.toString(), containsString("Message [id=12345,"));
    }
}
