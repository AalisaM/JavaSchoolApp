package jschool.dao.impl;

import jschool.dao.OrderProductHistoryDAO;
import jschool.model.OrderProductHistory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderProductHistoryDAOImpl implements OrderProductHistoryDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void remove(OrderProductHistory id) {
        this.entityManager.remove(id);
    }
}
