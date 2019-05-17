package jschool.dao.impl;

import jschool.dao.CartItemDAO;
import jschool.model.CartItem;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Repository
public class CartItemDAOImpl implements CartItemDAO {
    private static final Logger logger = Logger.getLogger(CartItemDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addCartItem(CartItem p) {
        this.entityManager.persist(p);
        logger.info("CartItem added successfully, CartItem details="+p);
    }

    @Override
    public void updateCartItem(CartItem p) {
        this.entityManager.merge(p);
        logger.info("CartItem updated successfully, CartItem details="+p);
    }

    @Override
    public void removeCartItemByID(CartItem ci){
        this.entityManager.remove(ci);
        logger.info("CartItem removed successfully");

    }

}
