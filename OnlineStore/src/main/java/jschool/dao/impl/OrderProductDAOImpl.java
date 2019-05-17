package jschool.dao.impl;

import jschool.dao.OrderProductDAO;
import jschool.model.OrderProduct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderProductDAOImpl implements OrderProductDAO {
    private static final Logger logger = Logger.getLogger(OrderProductDAO.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<OrderProduct> list() {
        List<OrderProduct> orderProducts = this.entityManager.createQuery("FROM " + OrderProduct.class.getSimpleName()).getResultList();
        logger.info("orderProducts list loaded successfully");
        return orderProducts;
    }

    @Override
    public OrderProduct findById(int id) {
        return null;
    }

    @Override
    public void update(OrderProduct p) {
        this.entityManager.merge(p);
        logger.info("orderProducts updated successfully::" + p.toString());
    }

    @Override
    public void add(OrderProduct p) {
        this.entityManager.persist(p);
        logger.info("orderProducts added successfully::" + p.toString());

    }

    @Override
    public void remove(OrderProduct id) {
        this.entityManager.remove(id);
        logger.info("orderProducts removed successfully::" + id);
    }


}
