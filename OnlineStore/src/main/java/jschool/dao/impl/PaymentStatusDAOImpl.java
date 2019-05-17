package jschool.dao.impl;

import jschool.dao.PaymentStatusDAO;
import jschool.model.PaymentStatus;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class PaymentStatusDAOImpl implements PaymentStatusDAO {
	
	private static final Logger logger = Logger.getLogger(UserDAOImpl.class);


	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public PaymentStatus findById(int id) {
		PaymentStatus p = this.entityManager.find(PaymentStatus.class, id);
		logger.info("Order loaded successfully, Order details="+p);
		return p;
	}

	@Override
	public PaymentStatus getByStatus(String status) {
		try{
			CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
			CriteriaQuery cq = cb.createQuery(PaymentStatus.class);

			Root<PaymentStatus> roles = cq.from(PaymentStatus.class);
			cq.where(cb.equal(roles.get("status"), status));

			List<PaymentStatus> result = this.entityManager.createQuery(cq).getResultList();

			logger.info("Payment status was found by name " + status);
			return result.isEmpty() ? null : result.get(0);
		}catch (Exception e){
			logger.error(e.toString());
			return null;
		}
	}
}
