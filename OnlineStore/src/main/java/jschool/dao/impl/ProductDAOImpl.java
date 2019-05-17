package jschool.dao.impl;

import jschool.dao.ProductDAO;
import jschool.model.Category;
import jschool.model.Product;
import org.apache.log4j.Logger;


import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

@Repository
public class ProductDAOImpl implements ProductDAO {
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> list() {
        List<Product> products = this.entityManager.createQuery("FROM " + Product.class.getSimpleName() + " WHERE deleted = false").getResultList();
        logger.info("Product list was loaded");
        return products;
    }

    @Override
    public Product findById(int id) {
        try {
            Product p = this.entityManager.find(Product.class, id);
            logger.info("Product loaded successfully, Product details="+p);
            return p;
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public Product findByIdForUpdate(int id) {
        try {
            return entityManager.find(Product.class, id, LockModeType.PESSIMISTIC_WRITE);
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public void update(Product p) {
        this.entityManager.persist(p);
        logger.info("Product updated successfully, Product Details="+p);
    }

    @Override
    public int add(Product p) {
        this.entityManager.persist(p);
        logger.info("Product saved successfully, Product Details="+p.getId());
        return p.getId();
    }

    @Override
    public void remove(int id) {
        try {
            Product p = this.entityManager.find(Product.class, id);
            if(null != p && !p.getDeleted()){
                p.setDeleted(true);
                p.setAmount(0);
                this.entityManager.merge(p);
            }
            logger.info("Product deleted successfully, Product details="+p);
        }catch (Exception e){
            logger.error(e.toString());
            throw e;
        }
    }

    /**
     * Changes prouct deleted status false
     * @param id product id
     */
    @Override
    public void revive(int id) {
        try {
            Product p = this.entityManager.find(Product.class, id);
            if(null != p && p.getDeleted()){
                p.setDeleted(false);
                this.entityManager.merge(p);
            }
            logger.info("Product revived successfully, Product details="+p);
        }catch (Exception e){
            logger.error(e.toString());
            throw e;
        }
    }


    @Override
    public void addImageToProduct(int id, String file) {
        Product p = this.entityManager.find(Product.class, id);
        p.setImageSource(file);
        this.entityManager.persist(p);
        logger.info("Product updated successfully, User Details="+p);
    }

    @Override
    public  List<Product> getProductsByFilter(int minPrice, int maxPrice, int minPlayer, int maxPlayer, int categoryId){
        try {
            logger.info("=================in dao filter===============");
            logger.info(" " + minPrice + maxPrice + " " + maxPlayer + " " + minPlayer + " ");
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            //select *  from product where price < price and maxPlayer < maxPlayer and minPlayer > minPlayer and Cat_id = Catid
            CriteriaQuery<Product> q = cb.createQuery(Product.class);
            Root<Product> c = q.from(Product.class);
            if (categoryId >= 0) {
                Join<Product, Category> categories = c.join("category");
                q.select(c)
                        .where(
                                cb.greaterThanOrEqualTo(c.get("price"), minPrice),
                                cb.lessThanOrEqualTo(c.get("price"), maxPrice),
                                cb.greaterThanOrEqualTo(c.get("minPlayerAmount"), minPlayer),
                                cb.lessThanOrEqualTo(c.get("maxPlayerAmount"), maxPlayer),
                                cb.equal(categories.get("id"), categoryId),
                                cb.equal(c.get("deleted"),false)
                        );
            } else {
                q.select(c)
                        .where(
                                cb.greaterThanOrEqualTo(c.get("price"), minPrice),
                                cb.lessThanOrEqualTo(c.get("price"), maxPrice),
                                cb.greaterThanOrEqualTo(c.get("minPlayerAmount"), minPlayer),
                                cb.lessThanOrEqualTo(c.get("maxPlayerAmount"), maxPlayer),
                                cb.equal(c.get("deleted"),false));
            }
            TypedQuery<Product> typedQuery = this.entityManager.createQuery(q);
            logger.info("product list from filter was got::" + Arrays.toString(typedQuery.getResultList().toArray()));
            return typedQuery.getResultList();
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public List<Product> searchByRequest(String name) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<Product> q = cb.createQuery(Product.class);
            Root<Product> root = q.from(Product.class);
            q.select(root).where(
                    cb.equal(root.get("deleted"),false),
                    cb.or(
                        cb.like(root.get("name"), '%' + name + '%'),
                        cb.like(root.get("description"), '%' + name + '%'))
            );
            TypedQuery<Product> typedQuery = this.entityManager.createQuery(q);
            logger.info(Arrays.toString(typedQuery.getResultList().toArray()));
            return typedQuery.getResultList();
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }

}
