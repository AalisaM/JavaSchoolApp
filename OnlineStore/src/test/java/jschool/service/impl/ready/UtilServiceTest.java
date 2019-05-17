package jschool.service.impl.ready;

import com.fasterxml.jackson.databind.JsonNode;
import jschool.configs.*;
import jschool.service.impl.UtilService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebConfig.class, ContextInitializer.class, SpringApplicationInitializer.class,
        AuthenticationSuccessHandlerImpl.class, SecurityConfig.class, SecurityWebApplicationInitializer.class})
public class UtilServiceTest {

    private final String json = "{\"data\": \"value\", \"intData\" : 0}";
    private final String incorrectJson = "{\"data\": \"value\", \"intData\" : }";

    @Test
    public void testParseJsonInput(){
        JsonNode jsonNode = UtilService.parseJsonInput(json);
        Assert.assertNotNull(jsonNode);
        Assert.assertEquals("value",jsonNode.get("data").asText());
        Assert.assertEquals(0,jsonNode.get("intData").asInt());
    }

    @Test
    public void testIncorrectJson(){
        JsonNode jsonNode1Wrong = UtilService.parseJsonInput(incorrectJson);
        Assert.assertNull(jsonNode1Wrong);
    }

}
