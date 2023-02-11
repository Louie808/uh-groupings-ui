package edu.hawaii.its.groupings.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootWebApplication.class })
public class AppConfigRunTest {

    @Test
    public void construction() {
        AppConfigRun appConfig = new AppConfigRun();
        Assertions.assertNotNull(appConfig);
    }

}
