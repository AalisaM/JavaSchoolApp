package jschool.dao;


import jschool.model.OrderProduct;

import java.util.List;

public interface OrderProductDAO {
     List<OrderProduct> list();
     OrderProduct findById(int id);
     void update(OrderProduct p);
     void add(OrderProduct p);
     void remove(OrderProduct id);

}
