package jschool.service.impl.ready;

import jschool.configs.*;
import jschool.service.CartService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.LinkedHashMap;
import java.util.LinkedList;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebConfig.class, ContextInitializer.class, SpringApplicationInitializer.class,
        AuthenticationSuccessHandlerImpl.class, SecurityConfig.class, SecurityWebApplicationInitializer.class})
public class CartServiceTest {
    @Autowired
    private CartService cartService;

    private static String anonCart;
    private static String incorrectJson;
    private static String missedProperty1;
    private static String missedProperty2;
    private static String noCartItems;

    @BeforeClass
    public static void setUp() {
        anonCart = "{\"totalAmount\":2," +
                    "\"totalPrice\":100," +
                    "\"cartItemArr\":[{\"product_id\":\"0\",\"product_name\":\"first\",\"amount\":1,\"price\":40},"+
                    "{\"product_id\":\"1\",\"product_name\":\"second\",\"amount\":2,\"price\":30}]}";
        incorrectJson =  "{\"data\": \"value\", \"intData\" : 3}";
        missedProperty1 ="{\"totalAmount\":2," +
                "\"cartItemArr\":[{\"product_id\":\"0\",\"product_name\":\"first\",\"amount\":1,\"price\":40},"+
                "{\"product_id\":\"1\",\"product_name\":\"second\",\"amount\":2,\"price\":30}]}";
        missedProperty2 = "{\"totalPrice\":100," +
                "\"cartItemArr\":[{\"product_id\":\"0\",\"product_name\":\"first\",\"amount\":1,\"price\":40},"+
                "{\"product_id\":\"1\",\"product_name\":\"second\",\"amount\":2,\"price\":30}]}";
        noCartItems = "{\"totalPrice\":100, \"totalAmount\":2,  \"cartItemArr\":[]}";
    }

    @Test
    public void testFormAnonymousJSON(){
        LinkedHashMap<String,Object> anon = this.cartService.formAnonymousCartByJSONString(anonCart);
        Assert.assertNotNull(anon);
        Assert.assertEquals(2,anon.get("totalAmount"));
        Assert.assertEquals(100,anon.get("totalPrice"));
        Assert.assertEquals(2, ((LinkedList<LinkedHashMap>)anon.get("cartItem")).size());
    }

    @Test
    public void testFormAnonymousWrongJSON(){
        LinkedHashMap<String,Object> anon = this.cartService.formAnonymousCartByJSONString(incorrectJson);
        Assert.assertNotNull(anon);
        Assert.assertTrue(anon.isEmpty());
    }

    @Test
    public void testFormAnonymousNullJson(){
        LinkedHashMap<String,Object> anon = this.cartService.formAnonymousCartByJSONString(null);
        Assert.assertNotNull(anon);
        Assert.assertTrue(anon.isEmpty());
    }

    @Test
    public void testMissedProperty1(){
        LinkedHashMap<String,Object> anon = this.cartService.formAnonymousCartByJSONString(missedProperty1);
        Assert.assertNotNull(anon);
    }

    @Test
    public void testMissedProperty2(){
        LinkedHashMap<String,Object> anon = this.cartService.formAnonymousCartByJSONString(missedProperty2);
        Assert.assertNotNull(anon);
    }

    @Test
    public void testCartItemNo(){
        LinkedHashMap<String,Object> anon = this.cartService.formAnonymousCartByJSONString(noCartItems);
        Assert.assertNotNull(anon);
        Assert.assertEquals(2,anon.get("totalAmount"));
        Assert.assertEquals(100,anon.get("totalPrice"));
        Assert.assertEquals(0, ((LinkedList<LinkedHashMap>)anon.get("cartItem")).size());
    }

}
