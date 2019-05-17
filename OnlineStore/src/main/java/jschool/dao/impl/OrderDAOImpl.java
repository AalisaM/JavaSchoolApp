package jschool.dao.impl;

import jschool.dao.OrderDAO;
import jschool.model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> list() {
        List<Order> orders = this.entityManager.createQuery("FROM " + Order.class.getSimpleName()).getResultList();
        logger.info("Order List loaded successfully");
        return orders;
    }

    @Override
    public Order findById(int id) {
        Order p = this.entityManager.find(Order.class, id);
        logger.info("Order loaded successfully, Order details="+p);
        return p;
    }


    @Override
    public Order update(Order o) {
        this.entityManager.merge(o);
        logger.info("Order updated successfully, Order Details="+o);
        return o;
    }

    @Override
    public Order add(Order c) {
        logger.info("Order saved successfully, Category Details="+c);
        this.entityManager.persist(c);
        logger.info("Order saved successfully, Category Details="+c);
        return c;

    }
}
