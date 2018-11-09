package ch.sebooom.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController controller
 */
@RestController
@RequestMapping("/api/test-controller")
public class TestControllerResource {

    private final Logger log = LoggerFactory.getLogger(TestControllerResource.class);

    /**
    * GET test
    */
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    /**
    * PUT testTest
    */
    @PutMapping("/test-test")
    public String testTest() {
        return "testTest";
    }

}
