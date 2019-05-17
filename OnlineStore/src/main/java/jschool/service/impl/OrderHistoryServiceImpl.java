package jschool.service.impl;

import jschool.dao.OrderAuditDAO;
import jschool.dao.OrderHistoryDAO;
import jschool.model.OrderAudit;
import jschool.model.OrderHistory;
import jschool.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private OrderHistoryDAO orderHistoryUserDAO;
    private OrderAuditDAO orderAuditDAO;

    @Autowired
    public OrderHistoryServiceImpl(OrderHistoryDAO orderHistoryUserDAO, OrderAuditDAO orderAuditDAO) {
        this.orderHistoryUserDAO = orderHistoryUserDAO;
        this.orderAuditDAO = orderAuditDAO;
    }

    /**
     * Get list of all entities in order history in db
     * @return list
     */
    @Override
    public List<OrderHistory> list() {
        return this.orderHistoryUserDAO.list();
    }

    /**
     * Get list of all order audit entities in db
     * @return list
     */
    @Override
    public List<OrderAudit> listAudit() {
        return this.orderAuditDAO.list();
    }
    /**
     * Get OrderHistory entity by id
     * @param id
     * @return entity
     */
    @Override
    public OrderHistory findById(int id) {
        return this.orderHistoryUserDAO.findById(id);
    }

    /**
     * Get OrderAudit entity by id
     * @param id
     * @return entity
     */
    @Override
    public OrderAudit findByIdAudit(int id) {
        return this.orderAuditDAO.findById(id);
    }


    /**
     * Update OrderHistory entity
     * @param c entity
     */
    @Override
    public void update(OrderHistory c) {
        this.orderHistoryUserDAO.update(c);
    }

    /**
     * update OrderAudit entity
     * @param c entity
     */
    @Override
    public void updateAudit(OrderAudit c) {
        this.orderAuditDAO.update(c);
    }

    /**
     * Add new entity to order history to db
     * @param c entity
     */
    @Override
    public void add(OrderHistory c) {
        this.orderHistoryUserDAO.add(c);
    }

    /**
     * Add new entity to order audit to db
     * @param c entity
     */
    @Override
    public void addAudit(OrderAudit c) {
        this.orderAuditDAO.add(c);
    }


}
