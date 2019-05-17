package jschool.service.impl;

import jschool.dao.CategoryDAO;
import jschool.dao.ProductDAO;
import jschool.dto.CategoryDTO;
import jschool.dto.CategoryRawDTO;
import jschool.model.Category;
import jschool.model.Product;
import jschool.service.CategoryService;
import jschool.service.DTOConverterService;
import jschool.validator.Message;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);

    private final DTOConverterService dtoConverterService;
    private final CategoryDAO categoryDAO;
    private final ProductDAO productDAO;

    @Autowired
    public CategoryServiceImpl(DTOConverterService dtoConverterService, CategoryDAO categoryDAO, ProductDAO productDAO) {
        this.dtoConverterService = dtoConverterService;
        this.categoryDAO = categoryDAO;
        this.productDAO = productDAO;
    }


    /**
     * Getting list of categories
     * @return CategoryRawDTO list
     */
    @Override
    @Transactional
    public List<CategoryRawDTO> list() {
        return  dtoConverterService.getCategoryRAWDTOList(this.categoryDAO.listCategory());
    }

    /**
     * Adding category to db
     * @param p category dto to add
     * @return Message object
     */
    @Override
    @Transactional
    public Message add(CategoryDTO p) {
        Message m  = new Message();
        try {
            Category c = new Category();
            c.setName(p.getName());
            c.setId(p.getId());
            c.setParentId(p.getParentId());
            this.categoryDAO.add(c);
            m.getConfirms().add("Category was successfully added");
        }catch (Exception e){
            logger.error(e.toString());
            m.getErrors().add("Category addition failed");
        }
        return m;
    }

    /**
     * Updating category in db
     * @param p category dto to update
     * @return Message Object
     */
    @Override
    @Transactional
    public Message update(CategoryDTO p) {
        Message m  = new Message();
        try {
            Category  c = this.categoryDAO.findById(p.getId());
            c.setName(p.getName());
            c.setId(p.getId());
            c.setParentId(p.getParentId());
            //products
            this.categoryDAO.update(c);
            m.getConfirms().add("Category was successfully updated");
        }catch (Exception e){
            logger.error(e.toString());
            m.getErrors().add("Category update failed");
        }
        return m;
    }

    /**
     * Getting Category from db by id
     * @param id id of category to get
     * @return  CategoryDTO object
     */
    @Override
    @Transactional
    public CategoryDTO findById(int id) {
        return dtoConverterService.getCategoryDTO(this.categoryDAO.findById(id));
    }


    /**
     * Making Category hidden. Moves all products to parent category
     * @param id id of category to remove
     * @return Message info object
     */
    @Override
    @Transactional
    public Message remove(int id) {
        Message m  = new Message();
        try {
            Category c = this.categoryDAO.remove(id);
            if  (!Objects.isNull(c.getParentId()) && c.getParentId() != 0){
                Category parent = this.categoryDAO.findById(c.getParentId());
                for (Product p: c.getProducts()){
                    p.setCategory(parent);
                    this.productDAO.update(p);
                }
                m.getConfirms().add("Category successfully removed");
            }else {
                m.getConfirms().add("You cannot remove root category");
            }
        }catch (Exception e){
            logger.error(e.toString());
            m.getErrors().add("Category removal failed");
        }
        return m;
    }

}
