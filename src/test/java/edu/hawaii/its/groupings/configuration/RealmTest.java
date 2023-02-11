package edu.hawaii.its.groupings.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { SpringBootWebApplication.class })
public class RealmTest {

    @Autowired
    private Realm realm;

    @Test
    public void basics() {
        Assertions.assertNotNull(realm);
        Assertions.assertTrue(realm.isDefault());
        Assertions.assertFalse(realm.isTest());
        Assertions.assertFalse(realm.isProduction());

        Assertions.assertFalse(realm.isProfileActive("not"));
        Assertions.assertFalse(realm.isProfileActive(""));
        Assertions.assertFalse(realm.isProfileActive(null));

        Assertions.assertFalse(realm.isAnyProfileActive());
        Assertions.assertFalse(realm.isAnyProfileActive((String) null));
        Assertions.assertFalse(realm.isAnyProfileActive("not"));
        Assertions.assertFalse(realm.isAnyProfileActive("not", "question"));
        Assertions.assertTrue(realm.isAnyProfileActive("not", "question", "default"));
        Assertions.assertFalse(realm.isAnyProfileActive("test", "prod"));

        // This test is using the 'default' profile.
        Assertions.assertTrue(realm.isAnyProfileActive("default"));
        Assertions.assertTrue(realm.isAnyProfileActive("test", "default"));
        Assertions.assertTrue(realm.isAnyProfileActive("test", "qa", "default"));
        Assertions.assertTrue(realm.isAnyProfileActive("test", "qa", "prod", "default"));

        Assertions.assertFalse(realm.isAnyProfileActive("test"));
        Assertions.assertFalse(realm.isAnyProfileActive("test", "qa"));
        Assertions.assertFalse(realm.isAnyProfileActive("test", "qa", "prod"));

        String[] array = null;
        Assertions.assertFalse(realm.isAnyProfileActive(array));
        array = new String[3];
        Assertions.assertFalse(realm.isAnyProfileActive(array));
        array[2] = "default";
        Assertions.assertTrue(realm.isAnyProfileActive(array));
    }

}
