package jschool.dao;

import jschool.model.OrderAudit;
import java.util.List;

public interface OrderAuditDAO {
        List<OrderAudit> list();
        List<OrderAudit> list(int id);

        OrderAudit findById(int id);

        void update(OrderAudit c);
        void add(OrderAudit c);
}
