package jschool.service.impl.ready;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.google.common.collect.Sets;
import jschool.dao.CartDAO;
import jschool.dao.CartItemDAO;
import jschool.dao.ProductDAO;
import jschool.dto.CartDTO;
import jschool.model.Cart;
import jschool.model.CartItem;
import jschool.model.Product;
import jschool.service.CartService;
import jschool.service.impl.CartItemServiceImpl;
import jschool.validator.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
public class CartItemServiceImplTest {

  @Mock
  private CartService mockCartService;
  @Mock
  private CartDAO mockCartDAO;
  @Mock
  private CartItemDAO mockCartItemDAO;
  @Mock
  private ProductDAO mockProductDAO;

  private CartItemServiceImpl cartItemServiceImplUnderTest;

  @BeforeEach
  public void setUp() {
    initMocks(this);
    cartItemServiceImplUnderTest = new CartItemServiceImpl(mockCartService, mockCartDAO, mockCartItemDAO,
        mockProductDAO);
  }

  @Test
  public void testAddProductToCart() {
    // Setup
    final Message m = new Message();
    final String json = "{\"id\" : 1}";

    CartDTO cartDTO = new CartDTO();
    cartDTO.setUser_id(1);
    Cart cart = new Cart();
    Product product = new Product();
    when(mockCartDAO.findById(1)).thenReturn(cart);
    when(mockCartService.getCurUserCart()).thenReturn(cartDTO);
    when(mockProductDAO.findById(1)).thenReturn(product);
    when(mockCartDAO.findCartItemByProductId(1, 1)).thenReturn(null);

    // Run the test 
    final Message result = cartItemServiceImplUnderTest.addProductToCart(m, json);

    // Verify the results
    assertEquals(1, result.getConfirms().size());
  }

  @Test
  public void testRemoveProductFromCart() {
    // Setup 
    final Message m = new Message();
    final String json = "{\"id\" :1, \"amount\" : 1}";

    CartDTO cartDTO = new CartDTO();
    cartDTO.setUser_id(1);
    Product product = new Product();
    CartItem cartItem = new CartItem();
    cartItem.setAmount(5);
    Cart cart = new Cart();
    cart.setCartItem(Sets.newHashSet(cartItem));
    when(mockCartDAO.findById(1)).thenReturn(cart);
    when(mockCartService.getCurUserCart()).thenReturn(cartDTO);
    when(mockProductDAO.findById(1)).thenReturn(product);
    when(mockCartDAO.findCartItemByProductId(1, 0)).thenReturn(cartItem);

    // Run the test 
    final Message result = cartItemServiceImplUnderTest.removeProductFromCart(m, json);

    // Verify the results
    assertEquals(1, result.getConfirms().size());
  }
}
