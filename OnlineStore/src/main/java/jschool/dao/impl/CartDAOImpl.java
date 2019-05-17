package jschool.dao.impl;

import jschool.dao.CartDAO;
import jschool.model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

@Repository
public class CartDAOImpl implements CartDAO {
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Cart findById(int id) {
        try{
            Cart p = this.entityManager.find(Cart.class, id);
            logger.info("Cart loaded successfully, cart details="+p);
            return p;
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }


    @Override
    public CartItem findCartItemByProductId(int id, int cartid){
        try{
            logger.info("=================in cart item find by product filter===============");
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery<CartItem> q = cb.createQuery(CartItem.class);
            Root<CartItem> c = q.from(CartItem.class);
            Join<CartItem, Product> products = c.join("product");
            Join<CartItem, Cart> carts = c.join("cart");
            q.select(c)
                    .where(
                            cb.equal(products.get("id"),id),
                            cb.equal(carts.get("userId"),cartid)
                            );
            TypedQuery<CartItem> typedQuery = this.entityManager.createQuery(q);
            logger.info(Arrays.toString(typedQuery.getResultList().toArray()));
            List<CartItem> result = typedQuery.getResultList();
            logger.info(result);

            return result.isEmpty() ? null : result.get(0);
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public void update(Cart c){
        try {
            this.entityManager.merge(c);
            logger.info("Cart updated successfulle="+c.getUser().toString() + c.getTotalAmount() + c.getTotalPrice());
        }catch (Exception e){
            logger.error(e.toString());
        }
    }

    @Override
    public void add(Cart c) {
        try {
            this.entityManager.persist(c);
            logger.info("Cart saved successfulle="+c.getUser().toString() + c.getTotalAmount() + c.getTotalPrice());
        }catch (Exception e){
            logger.error(e.toString());
        }
    }

    @Override
    public void remove(Cart ci){
        try {
            this.entityManager.remove(ci);
            logger.info("Cart removed successfully");
        }catch (Exception e){
            logger.error(e.toString());
        }
    }
}
