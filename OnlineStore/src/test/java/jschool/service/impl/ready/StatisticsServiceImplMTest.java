package jschool.service.impl.ready;

import jschool.dao.OrderHistoryDAO;
import jschool.dao.OrderProductDAO;
import jschool.dao.UserDAO;
import jschool.dto.ProductDTO;
import jschool.dto.ProductRestDTO;
import jschool.dto.UserDTO;
import jschool.model.*;
import jschool.service.DTOConverterService;
import jschool.service.impl.StatisticsServiceImpl;
import jschool.validator.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class StatisticsServiceImplMTest {

  @Mock
  private OrderHistoryDAO mockOrderHistoryDAO;
  @Mock
  private UserDAO mockUserDAO;
  @Mock
  private OrderProductDAO mockOrderProductDAO;
  @Mock
  private DTOConverterService mockDtoConverterService;

  private StatisticsServiceImpl statisticsServiceImplUnderTest;

  private LocalDate from;
  private LocalDate to ;
  private int limit;
  private Timestamp date;

  private User initUser(){
    User u = new User();
    u.setId(1);
    u.setEmail("a@a.a");
    return  u;
  }

  private PaymentStatus initPaidStatus(){
    PaymentStatus paymentStatus = new PaymentStatus();
    paymentStatus.setStatus("paid");
    paymentStatus.setId(1);
    return paymentStatus;
  }

  private PaymentStatus initNotPaidStatus(){
    PaymentStatus paymentStatus = new PaymentStatus();
    paymentStatus.setStatus("cancelled");
    paymentStatus.setId(2);
    return paymentStatus;
  }
  private OrderStatus initOrderStatus(){
    OrderStatus orderStatus = new OrderStatus();
    orderStatus.setStatus("paid");
    orderStatus.setId(1);
    return orderStatus;
  }

  private OrderStatus initBadOrderStatus(){
    OrderStatus orderStatus2 = new OrderStatus();
    orderStatus2.setId(2);
    orderStatus2.setStatus("cancelled");
    return orderStatus2;
  }

  private void initOrderHistories(){
    OrderHistory orderHistory = new OrderHistory();
    OrderHistory orderHistory2 = new OrderHistory();
    OrderHistory orderHistory3 = new OrderHistory();


    orderHistory.setDate(date);
    orderHistory2.setDate(date);
    orderHistory3.setDate(date);

    User u = initUser();

    orderHistory3.setOrderStatus(this.initOrderStatus());
    orderHistory.setOrderStatus(this.initOrderStatus());
    orderHistory2.setOrderStatus(this.initBadOrderStatus());

    orderHistory.setPaymentStatus(this.initPaidStatus());
    orderHistory2.setPaymentStatus(this.initPaidStatus());
    orderHistory3.setPaymentStatus(this.initNotPaidStatus());

    orderHistory.setTotalPrice(1000);
    orderHistory2.setTotalPrice(2000);
    orderHistory3.setTotalPrice(4000);

    orderHistory.setEmail(u.getEmail());
    orderHistory2.setEmail(u.getEmail());
    orderHistory3.setEmail(u.getEmail());

    List<OrderHistory> oh = new ArrayList<>();
    oh.add(orderHistory);
    oh.add(orderHistory2);
    oh.add(orderHistory3);

    when(mockOrderHistoryDAO.getOrdersByDatePeriod(from,to)).thenReturn(oh);
    when(mockOrderHistoryDAO.list()).thenReturn(oh);
    when(mockUserDAO.findByEmail(u.getEmail())).thenReturn(u);
  }

  private void initOrderProducts(){
    User u = this.initUser();
    Product p = new Product();
    Product p2 = new Product();
    Product p3 = new Product();

    p.setId(0);
    p2.setId(2);
    p3.setId(3);

    p.setName("p1");
    p2.setName("p2");
    p3.setName("p3");

    p.setPrice(500);
    p2.setPrice(1000);
    p3.setPrice(2000);

    Order o = new Order();
    o.setTotalPrice(1000);
    o.setOrderStatus(this.initOrderStatus());
    o.setPaymentStatus(this.initPaidStatus());
    o.setUser(u);
    o.setDate(date);
    o.setId(1);

    Order o2 = new Order();
    o2.setTotalPrice(2000);
    o2.setOrderStatus(this.initBadOrderStatus());
    o2.setPaymentStatus(this.initNotPaidStatus());
    o2.setUser(u);
    o2.setDate(date);
    o2.setId(2);

    OrderProduct orderProduct = new OrderProduct();
    orderProduct.setProduct(p);
    orderProduct.setAmount(2);
    orderProduct.setPrice(p.getPrice());
    orderProduct.setId(1);
    orderProduct.setOrder(o);

    OrderProduct orderProduct2 = new OrderProduct();
    orderProduct2.setProduct(p2);
    orderProduct2.setAmount(2);
    orderProduct2.setPrice(p2.getPrice());
    orderProduct2.setId(2);
    orderProduct2.setOrder(o);

    OrderProduct orderProduct3 = new OrderProduct();
    orderProduct3.setProduct(p3);
    orderProduct3.setAmount(2);
    orderProduct3.setPrice(p3.getPrice());
    orderProduct3.setId(3);
    orderProduct3.setOrder(o2);

    List<OrderProduct> orderProducts = new ArrayList<>();
    orderProducts.add(orderProduct);
    orderProducts.add(orderProduct2);
    orderProducts.add(orderProduct3);


//    UserDTO userDTO= new UserDTO();
//    userDTO.setId(u.getId());
//    userDTO.setEmail(u.getEmail());
//    userDTO.setFullName(u.getFullName());
//    when(mockDtoConverterService.getUserDTO(u)).thenReturn(userDTO);
    when(mockOrderProductDAO.list()).thenReturn(orderProducts);
  }

  @BeforeEach
  public void init() {
    MockitoAnnotations.initMocks(this);

    statisticsServiceImplUnderTest = new StatisticsServiceImpl(mockOrderHistoryDAO, mockUserDAO, mockOrderProductDAO,
            mockDtoConverterService);

    to = LocalDate.of(2019, 1, 1);
    from = LocalDate.of(2017, 1, 1);
    limit = 10;
    date =  Timestamp.valueOf(LocalDate.parse("2018-01-01").atStartOfDay());
    initOrderHistories();
    initOrderProducts();
  }

  @Test
  public void testGetRevenueForSelectedPeriod() {

    final Integer expectedResult = 3000;

    // Run the test
    final Integer result = statisticsServiceImplUnderTest.getRevenueForSelectedPeriod(from, to);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testGetRevenueForSelectedPeriod1() {
    // Setup
    final Message m = new Message();
    final String json = "{\"from\": \"2017-01-01\",\"to\": \"2019-01-01\"}";
    final Integer expectedResult = 3000;

    // Run the test
    final Integer result = statisticsServiceImplUnderTest.getRevenueForSelectedPeriod(m, json);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testGetMostValuableClientForSelectedPeriod() {
    // Run the test
    final Map<UserDTO, Integer> result = statisticsServiceImplUnderTest
        .getMostValuableClientForSelectedPeriod(to, from, limit);

    assertEquals(result.size(),1);
    assertTrue(result.containsValue(3000));
  }

  @Test
  public void testGetMostValuableClientForSelectedPeriod1() {
//    // Setup
    final String json = "{\"from\": \"2017-01-01\",\"to\": \"2019-01-01\", \"limit\" : 10}";

    final Map<UserDTO, Integer> result = statisticsServiceImplUnderTest.getMostValuableClientForSelectedPeriod(new Message(), json);

    assertEquals(result.size(),1);
    assertTrue(result.containsValue(3000));
  }

  @Test
  public void testGetMostOrderedProductsForSelectedPeriod() {
    assertEquals(1,  statisticsServiceImplUnderTest
            .getMostOrderedProductsForSelectedPeriod(to, from, limit).size());
  }

  @Test
  public void testGetMostOrderedProductsForSelectedPeriod1() {
    final String json = "{\"from\": \"2017-01-01\",\"to\": \"2019-01-01\", \"limit\" : 10}";

    final Map<ProductDTO, Integer> result = statisticsServiceImplUnderTest
        .getMostOrderedProductsForSelectedPeriod(new Message(), json);

    assertEquals(1, result.size());
  }

  @Test
  public void testGetRestProd() {

    Timestamp dbFrom = Timestamp.valueOf(from.atStartOfDay());
    Timestamp dbTo = Timestamp.valueOf(to.atStartOfDay());

    mockOrderProductDAO.list().stream()
            .filter(a -> a.getOrder().getDate().before(dbTo))
            .filter(a -> a.getOrder().getDate().after(dbFrom))
            .filter(a -> a.getOrder().isActive())
            .collect(Collectors.groupingBy(OrderProduct::getProduct,
                    Collectors.summingInt(OrderProduct::getAmount)));
    // Run the test
    final Map<ProductRestDTO, Integer> result = statisticsServiceImplUnderTest.getRestProd(to, from, limit);
    // Verify the results
    assertEquals(1, result.size());
  }
}
