package ch.sebooom.web.rest;

import ch.sebooom.SebHipsterApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the TestController REST controller.
 *
 * @see TestControllerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SebHipsterApp.class)
public class TestControllerResourceIntTest {

    private MockMvc restMockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        TestControllerResource testControllerResource = new TestControllerResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(testControllerResource)
            .build();
    }

    /**
    * Test test
    */
    @Test
    public void testTest() throws Exception {
        restMockMvc.perform(get("/api/test-controller/test"))
            .andExpect(status().isOk());
    }
    /**
    * Test testTest
    */
    @Test
    public void testTestTest() throws Exception {
        restMockMvc.perform(put("/api/test-controller/test-test"))
            .andExpect(status().isOk());
    }

}
