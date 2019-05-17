package jschool.dao;

import jschool.model.OrderStatus;

import java.util.List;

public interface OrderStatusDAO {
	 List<OrderStatus> list();
	 OrderStatus findById(int id);
	 OrderStatus getByStatus(String orderStatus);
}
