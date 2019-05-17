package jschool.dao;


import jschool.model.Order;

import java.util.List;

public interface OrderDAO {
    List<Order> list();
    Order findById(int id);
    Order update(Order o);
    Order add(Order c);
}
