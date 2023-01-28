package edu.hawaii.its.api.type;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GroupingsServiceResultTest {

    private GroupingsServiceResult groupingsServiceResult;

    @BeforeEach
    public void setUp() {
        groupingsServiceResult = new GroupingsServiceResult();
    }

    @Test
    public void groupingsServiceResultTest(){
        assertNotNull(groupingsServiceResult);
        groupingsServiceResult.setResultCode("resultCode");
        groupingsServiceResult.setAction("Action");
        assertThat(groupingsServiceResult.getResultCode(), equalTo("resultCode"));
        assertThat(groupingsServiceResult.getAction(), equalTo("Action"));
    }
}