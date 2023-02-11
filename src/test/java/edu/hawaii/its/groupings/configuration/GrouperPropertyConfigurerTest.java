package edu.hawaii.its.groupings.configuration;

import edu.internet2.middleware.grouperClient.util.GrouperClientConfig;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SpringBootWebApplication.class})
public class GrouperPropertyConfigurerTest {

    @Autowired
    private ConfigurableEnvironment env;

    @Autowired
    private GrouperPropertyConfigurer grouperPropertyConfigurer;

    @Test
    public void construction() {
        Assertions.assertNotNull(grouperPropertyConfigurer);
    }

    @Test
    public void testing() {
        GrouperClientConfig config = GrouperClientConfig.retrieveConfig();
        String key = "grouperClient.webService.url";
        String testUrl = "test-url-b";

        String value = config.propertiesOverrideMap().get(key);
        Assertions.assertNotEquals(value, testUrl);

        // Will cause an override of value.
        env.getSystemProperties().put(key, testUrl);

        grouperPropertyConfigurer.init();

        value = config.propertiesOverrideMap().get(key);
        Assertions.assertEquals(testUrl, value);
    }
}