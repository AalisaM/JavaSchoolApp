package jschool.integration;

import jschool.configs.*;
import jschool.dao.*;
import jschool.service.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static junit.framework.TestCase.assertNotNull;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebConfig.class, ContextInitializer.class, SpringApplicationInitializer.class,
        AuthenticationSuccessHandlerImpl.class, SecurityConfig.class, SecurityWebApplicationInitializer.class})
public class IntegrationTest extends AbstractJUnit4SpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private CartItemDAO cartItemDAO;


    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderAuditDAO orderAuditDAO;

    @Autowired
    private OrderProductHistoryDAO orderHistoryProductDAO;

    @Autowired
    private OrderHistoryDAO orderHistoryUserDAO;

    @Autowired
    private OrderProductDAO orderProductDAO;

    @Autowired
    private OrderStatusDAO orderStatusDAO;

    @Autowired
    private PaymentTypeDAO paymentTypeDAO;

    @Autowired
    private PaymentStatusDAO paymentStatusDAO;

    @Autowired
    private ShippingTypeDAO shippingTypeDAO;

    @Autowired
    private ProductDAO productDAO;


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DTOConverterService dtoConverterService;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private OrderHistoryService orderHistoryNewService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SMSSenderService smsSender;

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private UserService userService;


    @Test
    public void EntityManagerTest() {
        assertNotNull(em);
    }
    @Test
    public void  CartDAOTest(){
        assertNotNull(cartDAO);
    }

    @Test
    public void  CartItemDAOTest(){
        assertNotNull(cartItemDAO);
    }


    @Test
    public void CategoryDAOTest(){
        assertNotNull(categoryDAO);
    }

    @Test
    public void OrderDAOTest(){
        assertNotNull(orderDAO);
    }

    @Test
    public void OrderAuditDAOTest(){
        assertNotNull(orderAuditDAO);
    }

    @Test
    public void OrderHistoryProductDAOTest(){
        assertNotNull(orderHistoryProductDAO);
    }

    @Test
    public void OrderHistoryUserDAOTest(){
        assertNotNull(orderHistoryUserDAO);
    }

    @Test
    public void OrderProductDAOTest(){
        assertNotNull(orderProductDAO);
    }

    @Test
    public void OrderStatusDAOTest(){
        assertNotNull(orderStatusDAO);
    }

    @Test
    public void PaymentTypeDAOTest(){
        assertNotNull(paymentTypeDAO);
    }

    @Test
    public void PaymentStatusDAOTest(){
        assertNotNull(paymentStatusDAO);
    }

    @Test
    public void ShippingTypeDAOTest(){
        assertNotNull(shippingTypeDAO);
    }

    @Test
    public void ProductDAOTest(){
        assertNotNull(productDAO);
    }


    @Test
    public void UserDAOTest(){
        assertNotNull(userDAO);
    }

    @Test
    public void CartItemServiceTest(){
        assertNotNull(cartItemService);
    }

    @Test
    public void CartServiceTest(){
        assertNotNull(cartService);
    }

    @Test
    public void CategoryServiceTest(){
        assertNotNull(categoryService);
    }

    @Test
    public void DTOConverterServiceTest(){
        assertNotNull(dtoConverterService);
    }

    @Test
    public void MailSenderTest(){
        assertNotNull(mailSender);
    }

    @Test
    public void OrderHistoryNewServiceTest(){
        assertNotNull(orderHistoryNewService);
    }

    @Test
    public void OrderServiceTest(){
        assertNotNull(orderService);
    }

    @Test
    public void ProductServiceTest(){
        assertNotNull(productService);
    }

    @Test
    public void SMSSenderTest(){
        assertNotNull(smsSender);
    }

    @Test
    public void StatisticsServiceTest(){
        assertNotNull(statisticsService);
    }

    @Test
    public void UserServiceTest(){
        assertNotNull(userService);
    }



}
