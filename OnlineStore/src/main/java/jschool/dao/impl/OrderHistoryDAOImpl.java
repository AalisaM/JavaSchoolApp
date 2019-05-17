package jschool.dao.impl;

import jschool.dao.OrderHistoryDAO;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class OrderHistoryDAOImpl implements OrderHistoryDAO {
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderHistory> list() {
        List<OrderHistory> orderHistories = this.entityManager.createQuery("FROM " + OrderHistory.class.getSimpleName()).getResultList();
        logger.info("OrderHistory List loaded successfully");
        return orderHistories;
    }

    @Override
    public OrderHistory findById(int id) {
        try {
            OrderHistory p = this.entityManager.find(OrderHistory.class, id);
            logger.info("Order loaded successfully, Order details=" + p);
            return p;
        }catch(Exception e){
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public void update(OrderHistory c) {
        this.entityManager.merge(c);
        logger.info("OrderHistory updated successfully, Order Details="+c);
    }

    @Override
    public void add(OrderHistory c) {
        this.entityManager.persist(c);
        logger.info("OrderHistory saved successfully, Category Details="+c);
    }


    @Override
    public List<OrderHistory> getOrdersByDatePeriod(LocalDate from, LocalDate to) {
        try {
            Date dbFrom = Date.valueOf(from);
            Date dbTo = Date.valueOf(to);
            logger.info("in orderhistoryuser dao get by date: FROM:: " + from.toString() + ", TO:: " + to.toString());
            List<OrderHistory> orders = this.entityManager.createQuery("SELECT H FROM " + OrderHistory.class.getSimpleName() + " H WHERE H.date BETWEEN :fromDate AND :toDate ")
                    .setParameter("fromDate", dbFrom)
                    .setParameter("toDate", dbTo)
                    .getResultList();
            logger.info("orders by date found successfully");
            return orders;
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public List<OrderHistory> getUnpaidOrderList(int id) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            //select * from orderList where payment status not paid
            logger.info("OrderHistory get not paid orders");

            CriteriaQuery<OrderHistory> q = cb.createQuery(OrderHistory.class);
            Root<OrderHistory> c = q.from(OrderHistory.class);
            Join<OrderHistory, PaymentStatus> statuses = c.join("paymentStatus");

            if (id > 0) {
                Join<OrderHistory, User> users = c.join("user");
                q.select(c)
                        .where(
                                cb.equal(statuses.get("status"), "not paid"),
                                cb.equal(users.get("id"), id)

                        );
            } else {
                q.select(c)
                        .where(
                                cb.equal(statuses.get("status"), "not paid")
                        );
            }
            TypedQuery<OrderHistory> typedQuery = this.entityManager.createQuery(q);
            logger.info("orders for user found successfully");
            return typedQuery.getResultList();
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public List<OrderHistory> getPaidOrderList(int id) {
        try{
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            //select * from orderList where payment status not paid
            logger.info("OrderHistory get paid orders");

            CriteriaQuery<OrderHistory> q = cb.createQuery(OrderHistory.class);
            Root<OrderHistory> c = q.from(OrderHistory.class);
            Join<OrderHistory, PaymentStatus> statuses = c.join("paymentStatus");
            Join<OrderHistory, OrderStatus> orderStatuses = c.join("orderStatus");

            if (id > 0) {
                Join<OrderHistory, User> users = c.join("user");
                q.select(c)
                        .where(
                                cb.equal(statuses.get("status"), "paid"),
                                cb.equal(orderStatuses.get("status"), "waiting for delivery"),
                                cb.equal(users.get("id"), id)

                        );
            }else {
                q.select(c)
                        .where(
                                cb.equal(statuses.get("status"), "paid"),
                                cb.equal(orderStatuses.get("status"), "waiting for delivery")
                        );
            }
            TypedQuery<OrderHistory> typedQuery = this.entityManager.createQuery(q);
            logger.info("orders for user found successfully");
            return typedQuery.getResultList();
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public List<OrderHistory> getProcessedOrderList(int id) {
        try{
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            //select * from orderList where orderStatus id = 3 or 4
            logger.info("OrderHistory get delivered orders");

            CriteriaQuery<OrderHistory> q = cb.createQuery(OrderHistory.class);
            Root<OrderHistory> c = q.from(OrderHistory.class);
            Join<OrderHistory, OrderStatus> statuses = c.join("orderStatus");

            if (id > 0) {
                Join<Order, User> users = c.join("user");

                q.select(c)
                        .where(
                                cb.or(
                                        cb.equal(statuses.get("status"), "delivered")
                                ),
                                cb.equal(users.get("id"), id)
                        );
            }else {
                q.select(c)
                        .where(
                                cb.or(
                                        cb.equal(statuses.get("status"), "delivered")
                                )
                        );
            }
            TypedQuery<OrderHistory> typedQuery = this.entityManager.createQuery(q);
            logger.info("orders for user found successfully");
            return typedQuery.getResultList();
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }
}
