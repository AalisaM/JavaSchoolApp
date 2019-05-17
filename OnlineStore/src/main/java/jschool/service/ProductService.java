package jschool.service;

import jschool.dto.CategoryRawDTO;
import jschool.dto.ProductDTO;
import jschool.model.Category;
import jschool.model.Product;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import jschool.validator.Message;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Product service interface
 */
public interface ProductService {
    /**
     * Get list of product entities from db
     * @return list
     */
    List<Product> list();

    /**
     * Get list of product dto
     * @return list
     */
    Set<ProductDTO> listDTO();

    /**
     * Update product by dto
     * @param p dto
     * @return Message object info
     */
    Message update(ProductDTO p);

    /**
     * Get product by id
     * @param id id
     * @return product
     */
    Product findById(int id);

    /**
     * Get product dto by id
     * @param id id
     * @return product
     */
    ProductDTO findByIdFTO(int id);

    /**
     * Remove product by id
     * @param id id
     * @return message with remove result
     */
    Message remove(int id);

    /**
     * Revives product by id
     * @param id id
     * @return message with revive result
     */
    Message revive(int id);

    /**
     * Add product to db by dto
     * @param p dto
     */
    void add(ProductDTO p);

    /**
     * Add product with image to db
     * @param p productdto
     * @param f image
     * @return Message object info
     */
    Message addWithImage(ProductDTO p, MultipartFile f);

    /**
     * Edit product with image to db
     * @param p productdto
     * @param f image
     * @return Message object info
     */
    Message editWithImage(ProductDTO p, MultipartFile f);

    /**
     * Get filtered list of products
     * @param json input filter data {'minPlayer' :, 'maxPlayer' : , 'minPrice' :, 'maxPrice' : , 'categoryId' :}
     * @return list
     * @throws IOException
     */
    List<Product> filter(String json) throws IOException;

    /**
     * Get filtered dto list of products
     * @param json input filter data {'minPlayer' :, 'maxPlayer' : , 'minPrice' :, 'maxPrice' : , 'categoryId' :}
     * @return list
     * @throws IOException
     */
    Set<ProductDTO> filterDTO(String json) throws IOException;

    /**
     * Get dto list of products by search phrase
     * @param json search phrase
     * @return list
     * @throws IOException
     */
    Set<ProductDTO> search(String json) throws IOException;

    /**
     * Get list of categories
     * @return list
     */
    List<Category> listCategory();

    /**
     * Get list of categories dto without foreign lists
     * @return list
     */
    List<CategoryRawDTO> listCategoryDTO();


}
