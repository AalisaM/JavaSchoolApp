package jschool.dao;

import jschool.model.OrderHistory;

import java.time.LocalDate;
import java.util.List;

public interface OrderHistoryDAO {
        List<OrderHistory> list();
        OrderHistory findById(int id);

        void update(OrderHistory c);
        void add(OrderHistory c);

        List<OrderHistory> getOrdersByDatePeriod(LocalDate from, LocalDate to);
        List<OrderHistory> getUnpaidOrderList(int id);
        List<OrderHistory> getPaidOrderList(int id);
        List<OrderHistory> getProcessedOrderList(int id);
}
