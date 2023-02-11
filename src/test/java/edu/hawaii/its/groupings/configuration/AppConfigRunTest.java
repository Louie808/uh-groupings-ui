package edu.hawaii.its.groupings.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { SpringBootWebApplication.class })
public class AppConfigRunTest {

    @Test
    public void construction() {
        AppConfigRun appConfig = new AppConfigRun();
        Assertions.assertNotNull(appConfig);
    }

}
