package edu.hawaii.its.groupings.access;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;

public class UserTest {

    @Test
    public void construction() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();

        User user = new User("a", authorities);
        Assertions.assertNotNull(user);

        MatcherAssert.assertThat(user.getUsername(), is("a"));
        MatcherAssert.assertThat(user.getUid(), is("a"));
        Assertions.assertNull(user.getUhUuid());
        Assertions.assertNull(user.getAttributes());

        authorities = new LinkedHashSet<>();
        authorities.add(new SimpleGrantedAuthority(Role.ANONYMOUS.longName()));
        user = new User("b", "12345", authorities);

        MatcherAssert.assertThat(user.getUsername(), is("b"));
        MatcherAssert.assertThat(user.getUid(), is("b"));
        MatcherAssert.assertThat(user.getUhUuid(), is("12345"));
        Assertions.assertNull(user.getAttributes());

        user.setAttributes(new UhCasAttributes());
        MatcherAssert.assertThat(user.getName(), is(""));
    }

    @Test
    public void accessors() {
        Map<Object, Object> map = new HashMap<>();
        map.put("uid", "duckart");
        map.put("uhUuid", "666666");
        map.put("cn", "Frank6");
        map.put("givenName", "Frank");
        map.put("mail", "frank@example.com");
        map.put("eduPersonAffiliation", "aff");

        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        User user = new User("a", authorities);
        user.setAttributes(new UhCasAttributes(map));

        MatcherAssert.assertThat(user.getAttribute("uid"), is("duckart"));
        MatcherAssert.assertThat(user.getName(), is("Frank6"));
        MatcherAssert.assertThat(user.getGivenName(), is("Frank"));
        MatcherAssert.assertThat(user.toString(), containsString("uid=a"));
        MatcherAssert.assertThat(user.toString(), containsString("uhUuid=null"));
    }
}
