package jschool.dao.impl;

import jschool.dao.OrderStatusDAO;
import jschool.model.OrderStatus;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class OrderStatusDAOImpl implements OrderStatusDAO {
	
	private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<OrderStatus> list() {
		List<OrderStatus> categories = this.entityManager.createQuery("FROM " + OrderStatus.class.getSimpleName()).getResultList();
		for(OrderStatus p : categories){
			logger.info("OrderStatus List::"+p);
		}
		return categories;
	}

	@Override
	public OrderStatus findById(int id) {
		OrderStatus p = this.entityManager.find(OrderStatus.class, id);
		logger.info("OrderStatus loaded successfully, OrderStatus details="+p);
		return p;
	}


	@Override
	public OrderStatus getByStatus(String orderStatus) {
		try {
			CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();

			CriteriaQuery cq = cb.createQuery(OrderStatus.class);
			Root<OrderStatus> roles = cq.from(OrderStatus.class);
			cq.where(cb.equal(roles.get("status"), orderStatus));

			List<OrderStatus> result = this.entityManager.createQuery(cq).getResultList();
			logger.info("OrderStatus was found by name " + orderStatus);

			return result.isEmpty() ? null : result.get(0);
		}catch (Exception e){
			logger.error(e.toString());
			return null;
		}
	}
}
