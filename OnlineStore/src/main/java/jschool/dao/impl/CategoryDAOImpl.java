package jschool.dao.impl;

import jschool.dao.CategoryDAO;
import jschool.model.Category;
import org.apache.log4j.Logger;

import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class CategoryDAOImpl implements CategoryDAO {
    private static final Logger logger =  Logger.getLogger(UserDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Category findById(int id) {
        Category p = this.entityManager.find(Category.class, id);
        logger.info("Category loaded successfully, Category details="+p);
        return p;
    }

    @Override
    public Category findByName(String name) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();

            CriteriaQuery cq = cb.createQuery(Category.class);
            Root<Category> roles = cq.from(Category.class);
            cq.where(cb.equal(roles.get("name"), name));

            List<Category> result = this.entityManager.createQuery(cq).getResultList();
            logger.info("category was found by name" + name);
            return result.isEmpty() ? null : result.get(0);
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public List<Category> listCategory() {
        try {
            List<Category> categories = this.entityManager.createQuery("FROM " + Category.class.getSimpleName()
                    + " WHERE isDeleted = false").getResultList();
            logger.info("category list was loaded successfully ( deleted categories were ignored)");
            return categories;
        }catch (Exception e){
            logger.error(e.toString());
            return  null;
        }
    }

    @Override
    public void update(Category p) {
        this.entityManager.merge(p);
        logger.info("Category updated successfully, Category Details="+p);
    }

    @Override
    public void add(Category p) {
        p.setDeleted(false);
        this.entityManager.persist(p);
        logger.info("Category saved successfully, Category Details="+p);
    }

    @Override
    public Category remove(int id) {
        Category p = this.entityManager.find(Category.class, id);
        if(null != p){
            p.setDeleted(true);
            this.entityManager.merge(p);
        }
        logger.info("Category deleted successfully, Category details="+p);
        return p;
    }

}
