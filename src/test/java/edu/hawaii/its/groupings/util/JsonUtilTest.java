package edu.hawaii.its.groupings.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import edu.hawaii.its.groupings.type.Feedback;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class JsonUtilTest {

    @Test
    public void basics() {
        Feedback fb0 = new Feedback("message");
        String fbJson = JsonUtil.asJson(fb0);

        Feedback fb1 = JsonUtil.asObject(fbJson, Feedback.class);

        Assertions.assertEquals(fb0.getName(), fb1.getName());
        Assertions.assertEquals(fb0.getEmail(), fb1.getEmail());
        Assertions.assertEquals(fb0.getType(), fb1.getType());
        Assertions.assertEquals(fb0.getMessage(), fb1.getMessage());
        Assertions.assertEquals(fb0.getExceptionMessage(), fb1.getExceptionMessage());
    }Assertions.

    @Test
    public void problems() {
        String json = JsonUtil.asJson(null);
        Assertions.assertEquals(json, "null");

        json = JsonUtil.asJson("{}");
        Assertions.assertEquals(json, "\"{}\"");

        json = JsonUtil.asJson("mistake");
        Assertions.assertEquals(json, "\"mistake\"");
    }

    @Test
    public void constructorIsPrivate() throws Exception {
        Constructor<JsonUtil> constructor = JsonUtil.class.getDeclaredConstructor();
        Assertions.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }
}
