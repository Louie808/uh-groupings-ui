package edu.hawaii.its.groupings.access;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.authentication.AttributePrincipalImpl;
import org.jasig.cas.client.authentication.SimplePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.AssertionImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.hawaii.its.api.controller.GroupingsRestController;
import edu.hawaii.its.groupings.configuration.SpringBootWebApplication;

@SpringBootTest(classes = { SpringBootWebApplication.class })
public class UserDetailsServiceTest {

    @Autowired
    private UserBuilder userBuilder;

    @MockBean
    private GroupingsRestController groupingsRestController;

    @Test
    public void construction() {
        assertThat(userBuilder, not(equalTo(null)));
    }

    @Test
    public void testAdminUser() {
        final String uhUuid = "89999999";

        // Make up a user.
        Map<String, Object> map = new HashMap<>();
        map.put("uid", "duckart");
        map.put("uhUuid", uhUuid);
        AttributePrincipal principal = new AttributePrincipalImpl("duckart", map);
        Assertion assertion = new AssertionImpl(principal);
        CasUserDetailsServiceImpl userDetailsService = new CasUserDetailsServiceImpl(userBuilder);

        // Mock the role fetching from the Grouper server.
        given(groupingsRestController.hasOwnerPrivs(new SimplePrincipal(uhUuid)))
                .willReturn(new ResponseEntity<>("true", HttpStatus.OK));
        given(groupingsRestController.hasAdminPrivs(new SimplePrincipal(uhUuid)))
                .willReturn(new ResponseEntity<>("true", HttpStatus.OK));

        // What we are testing.
        User user = (User) userDetailsService.loadUserDetails(assertion);

        // Basics.
        assertThat(user.getUsername(), equalTo("duckart"));
        assertThat(user.getUid(), equalTo("duckart"));
        assertThat(user.getUhUuid(), equalTo("89999999"));

        // Granted Authorities.
        assertThat(user.getAuthorities().size(), equalTo(4));
        assertTrue(user.hasRole(Role.ANONYMOUS));
        assertTrue(user.hasRole(Role.UH));
        assertTrue(user.hasRole(Role.OWNER));
        assertTrue(user.hasRole(Role.ADMIN));
    }

    @Test
    public void testOwner() {
        final String uid = "jjcale";
        final String uhUuid = "10000004";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("uhUuid", uhUuid);

        AttributePrincipal principal = new AttributePrincipalImpl("jjcale", map);
        Assertion assertion = new AssertionImpl(principal);
        CasUserDetailsServiceImpl userDetailsService = new CasUserDetailsServiceImpl(userBuilder);

        // Mock the role fetching from the Grouper server.
        final Principal p = new SimplePrincipal(uhUuid);
        given(groupingsRestController.hasOwnerPrivs(p))
                .willReturn(new ResponseEntity<>("true", HttpStatus.OK));
        given(groupingsRestController.hasAdminPrivs(p))
                .willReturn(new ResponseEntity<>("false", HttpStatus.OK)); // Note.

        // What we are testing.
        User user = (User) userDetailsService.loadUserDetails(assertion);

        // Basics.
        assertThat(user.getUsername(), equalTo("jjcale"));
        assertThat(user.getUid(), equalTo("jjcale"));
        assertThat(user.getUhUuid(), equalTo("10000004"));

        // Granted Authorities.
        assertThat(user.getAuthorities().size(), equalTo(3));
        assertTrue(user.hasRole(Role.ANONYMOUS));
        assertTrue(user.hasRole(Role.UH));
        assertTrue(user.hasRole(Role.OWNER));

        assertFalse(user.hasRole(Role.ADMIN));
    }

    @Test
    public void loadUserDetailsExceptionOne() {
        Assertion assertion = new AssertionDummy();
        CasUserDetailsServiceImpl userDetailsService = new CasUserDetailsServiceImpl(userBuilder);
        try {
            userDetailsService.loadUserDetails(assertion);
            fail("Should not have reached here.");
        } catch (Exception e) {
            assertThat(UsernameNotFoundException.class, equalTo(e.getClass()));
            assertThat(e.getMessage(), containsString("principal is null"));
        }
    }
}
