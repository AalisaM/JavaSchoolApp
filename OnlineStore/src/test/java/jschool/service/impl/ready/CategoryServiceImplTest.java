package jschool.service.impl.ready;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;
import jschool.dao.CategoryDAO;
import jschool.dao.ProductDAO;
import jschool.dto.CategoryDTO;
import jschool.dto.CategoryRawDTO;
import jschool.model.Category;
import jschool.service.DTOConverterService;
import jschool.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class CategoryServiceImplTest {

  @Mock
  private DTOConverterService mockDtoConverterService;
  @Mock
  private CategoryDAO mockCategoryDAO;
  @Mock
  private ProductDAO productDAO;

  private CategoryServiceImpl categoryServiceImplUnderTest;

  @BeforeEach
  public void setUp() {
    initMocks(this);
    categoryServiceImplUnderTest = new CategoryServiceImpl(mockDtoConverterService, mockCategoryDAO, productDAO);
  }

  @Test
  public void testList() {
    // Setup 
    final List<CategoryRawDTO> expectedResult = Arrays.asList();
    when(mockDtoConverterService.getCategoryRAWDTOList(Arrays.asList())).thenReturn(
        Arrays.asList());
    when(mockCategoryDAO.listCategory()).thenReturn(Arrays.asList());

    // Run the test 
    final List<CategoryRawDTO> result = categoryServiceImplUnderTest.list();

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testAdd() {
    // Setup 
    final CategoryDTO p = mock(CategoryDTO.class);
    Category c = mock(Category.class);

    // Run the test 
    categoryServiceImplUnderTest.add(p);

    // Verify the results
    verify(mockCategoryDAO).add(any());
  }

  @Test
  public void testUpdate() {
    // Setup 
    final CategoryDTO p = mock(CategoryDTO.class);
    when(mockCategoryDAO.findById(0)).thenReturn(mock(Category.class));

    // Run the test 
    categoryServiceImplUnderTest.update(p);

    // Verify the results
    verify(mockCategoryDAO).update(any());
  }

  @Test
  public void testFindById() {
    // Setup 
    final int id = 0;
    final CategoryDTO expectedResult = null;
    when(mockDtoConverterService.getCategoryDTO(null)).thenReturn(null);
    when(mockCategoryDAO.findById(0)).thenReturn(null);

    // Run the test 
    final CategoryDTO result = categoryServiceImplUnderTest.findById(id);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  public void testRemove() {
    // Setup 
    final int id = 0;

    // Run the test 
    categoryServiceImplUnderTest.remove(id);

    // Verify the results
    verify(mockCategoryDAO).remove(0);
  }
}
