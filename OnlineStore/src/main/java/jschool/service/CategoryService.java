package jschool.service;

import jschool.dto.CategoryDTO;
import jschool.dto.CategoryRawDTO;
import jschool.validator.Message;

import java.util.List;

/**
 * Category Service interface
 */
public interface CategoryService {
     /**
      * Getting list of categories
      * @return CategoryRawDTO list
      */
     List<CategoryRawDTO> list();

     /**
      * Adding category to db
      * @param p category dto to add
      * @return Message object
      */
     Message add(CategoryDTO p);

     /**
      * Updating category in db
      * @param p category dto to update
      * @return Message Object
      */
     Message update(CategoryDTO p);

     /**
      * Getting Category from db by id
      * @param id id of category to get
      * @return  CategoryDTO object
      */
     CategoryDTO findById(int id);

     /**
      * Remoing Category from db
      * @param id id of category to remove
      * @return Message info object
      */
     Message remove(int id);
}
