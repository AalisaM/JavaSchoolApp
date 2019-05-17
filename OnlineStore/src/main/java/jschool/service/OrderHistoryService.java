package jschool.service;

import jschool.model.OrderAudit;
import jschool.model.OrderHistory;

import java.util.List;

/**
 * OrderHistory service interface
 */
public interface OrderHistoryService {
     /**
      * Get list of all entities in order history in db
      * @return
      */
     List<OrderHistory> list();

     /**
      * Get list of all order audit entities in db
      * @return
      */
     List<OrderAudit> listAudit();

     /**
      * Get OrderHistory entity by id
      * @param id
      * @return
      */
     OrderHistory findById(int id);

     /**
      * Get OrderAudit entity by id
      * @param id
      * @return
      */
     OrderAudit findByIdAudit(int id);

     /**
      * Update OrderHistory entity
      * @param c
      */
     void update(OrderHistory c);

     /**
      * update OrderAudit entity
      * @param c
      */
     void updateAudit(OrderAudit c);

     /**
      * Add new entity to order history to db
      * @param c
      */
     void add(OrderHistory c);

     /**
      * Add new entity to order audit to db
      * @param c
      */
     void addAudit(OrderAudit c);
}
