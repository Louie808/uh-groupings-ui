package edu.hawaii.its.groupings.access;

import edu.hawaii.its.groupings.configuration.SpringBootWebApplication;
import edu.hawaii.its.groupings.controller.WithMockUhUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { SpringBootWebApplication.class })
public class UserContextServiceTest {

    @Autowired
    private UserContextService userContextService;

    @Test
    @WithMockUhUser(username = "admin", roles = { "ROLE_ADMIN" })
    public void basics() {
        Assertions.assertEquals("12345678", userContextService.getCurrentUhUuid());
        Assertions.assertEquals("admin", userContextService.getCurrentUid());
        Assertions.assertTrue(userContextService.toString().startsWith("UserContextServiceImpl"));

        User user = userContextService.getCurrentUser();
        Assertions.assertNotNull(user);
        Assertions.assertEquals("12345678", user.getUhUuid());
        Assertions.assertEquals("admin", user.getUid());

        userContextService.setCurrentUhUuid("87654321");
        Assertions.assertEquals("87654321", userContextService.getCurrentUhUuid());
    }
    @Test
    @WithMockUhUser(username = "Owner", roles = { "ROLE_OWNER"})
    public void testOwner(){
        User user = userContextService.getCurrentUser();
        Assertions.assertFalse(user.hasRole(Role.ADMIN));
        Assertions.assertTrue(user.hasRole(Role.OWNER));
    }
}


