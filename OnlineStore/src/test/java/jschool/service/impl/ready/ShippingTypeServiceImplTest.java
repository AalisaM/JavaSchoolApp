package jschool.service.impl.ready;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;
import jschool.dao.ShippingTypeDAO;
import jschool.dto.ShippingDTO;
import jschool.model.ShippingType;
import jschool.service.DTOConverterService;
import jschool.service.impl.ShippingTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ShippingTypeServiceImplTest {

  @Mock
  private DTOConverterService mockDtoConverterService;
  @Mock
  private ShippingTypeDAO mockShippingTypeDAO;

  private ShippingTypeServiceImpl shippingTypeServiceImplUnderTest;

  @BeforeEach
  public void setUp() {
    initMocks(this);
    shippingTypeServiceImplUnderTest = new ShippingTypeServiceImpl(mockDtoConverterService, mockShippingTypeDAO);
  }

  @Test
  public void testAdd() {
    // Setup 
    final ShippingDTO p = mock(ShippingDTO.class);

    // Run the test 
    shippingTypeServiceImplUnderTest.add(p);

    // Verify the results
    verify(mockShippingTypeDAO).add(any());
  }

  @Test
  public void testUpdate() {
    // Setup 
    final ShippingDTO p = mock(ShippingDTO.class);
    when(mockShippingTypeDAO.findById(0)).thenReturn(mock(ShippingType.class));

    // Run the test 
    shippingTypeServiceImplUnderTest.update(p);

    // Verify the results
    verify(mockShippingTypeDAO).update(any());
  }

  @Test
  public void testList() {
    // Setup 
    final List<ShippingType> expectedResult = Arrays.asList();
    when(mockShippingTypeDAO.list()).thenReturn(Arrays.asList());

    // Run the test 
    final List<ShippingType> result = shippingTypeServiceImplUnderTest.list();

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testListDTO() {
    // Setup 
    final List<ShippingDTO> expectedResult = Arrays.asList();
    when(mockDtoConverterService.getShippingDTOList(Arrays.asList())).thenReturn(
        Arrays.asList());
    when(mockShippingTypeDAO.list()).thenReturn(Arrays.asList());

    // Run the test 
    final List<ShippingDTO> result = shippingTypeServiceImplUnderTest.listDTO();

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testFindById() {
    // Setup 
    final int id = 0;
    final ShippingType expectedResult = null;
    when(mockShippingTypeDAO.findById(0)).thenReturn(null);

    // Run the test 
    final ShippingType result = shippingTypeServiceImplUnderTest.findById(id);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testFindByIdDTo() {
    // Setup 
    final int id = 0;
    final ShippingDTO expectedResult = null;
    when(mockDtoConverterService.getShippingDTO(null)).thenReturn(null);
    when(mockShippingTypeDAO.findById(0)).thenReturn(null);

    // Run the test 
    final ShippingDTO result = shippingTypeServiceImplUnderTest.findByIdDTo(id);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testRemove() {
    // Setup 
    final int id = 0;

    // Run the test 
    shippingTypeServiceImplUnderTest.remove(id);

    // Verify the results
    verify(mockShippingTypeDAO).remove(0);
  }
}
