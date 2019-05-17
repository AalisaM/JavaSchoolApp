package jschool.dao.impl;

import jschool.dao.OrderAuditDAO;
import jschool.model.Order;
import jschool.model.OrderAudit;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
class OrderAuditDAOImpl implements OrderAuditDAO {
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<OrderAudit> list() {
        List<OrderAudit> orderAudits = this.entityManager.createQuery("FROM " + OrderAudit.class.getSimpleName()).getResultList();
        logger.info("OrderAudit List loaded successfully");
        return orderAudits;
    }

    @Override
    public List<OrderAudit> list(int id) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            //select * from orderList where payment status not paid
            CriteriaQuery<OrderAudit> q = cb.createQuery(OrderAudit.class);
            Root<OrderAudit> c = q.from(OrderAudit.class);
            Join<OrderAudit, Order> orderStatuses = c.join("order");
            q.select(c)
                    .where(
                            cb.equal(orderStatuses.get("id"), id)
                    );

            TypedQuery<OrderAudit> typedQuery = this.entityManager.createQuery(q);
            logger.info("orderauditlist for specific order loaded. id::" + id);
            return typedQuery.getResultList();
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }


    @Override
    public OrderAudit findById(int id) {
        OrderAudit p = this.entityManager.find(OrderAudit.class, id);
        logger.info("OrderAudit loaded successfully, Order details="+p);
        return p;
    }

    @Override
    public void update(OrderAudit c) {
        this.entityManager.merge(c);
        logger.info("OrderAudit updated successfully, Order Details="+c);
    }

    @Override
    public void add(OrderAudit c) {
        this.entityManager.persist(c);
        logger.info("OrderAudit saved successfully, Category Details="+c);
    }
}
