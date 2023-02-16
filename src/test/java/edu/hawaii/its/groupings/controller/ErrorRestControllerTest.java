package edu.hawaii.its.groupings.controller;

import edu.hawaii.its.groupings.configuration.SpringBootWebApplication;
import edu.hawaii.its.groupings.type.Feedback;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { SpringBootWebApplication.class })
public class ErrorRestControllerTest {

    @Autowired
    private ErrorRestController restController;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    public void testConstruction() {
        Assertions.assertNotNull(restController);
    }

    @Test
    public void httpPostFeedbackError() throws Exception {
        HttpSession session = mockMvc.perform(post("/feedback/error")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"exceptionMessage\":\"exception\"}"))
                .andExpect(status().isNoContent())
                .andReturn()
                .getRequest()
                .getSession();

        assert session != null;
        Assertions.assertNotNull(session.getAttribute("feedback"));
        Feedback feedback = (Feedback) session.getAttribute("feedback");
        MatcherAssert.assertThat(feedback.getExceptionMessage(), is("exception"));
    }

}
