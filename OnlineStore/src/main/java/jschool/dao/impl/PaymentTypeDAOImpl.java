package jschool.dao.impl;

import jschool.dao.PaymentTypeDAO;

import jschool.model.PaymentType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class PaymentTypeDAOImpl implements PaymentTypeDAO {
	
	private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public List<PaymentType> list() {
		List<PaymentType> categories = this.entityManager.createQuery("FROM " + PaymentType.class.getSimpleName()).getResultList();
		for(PaymentType p : categories){
			logger.info("PaymentType List::"+p);
		}
		return categories;
	}

	@Override
	public PaymentType findById(int id) {
		PaymentType p = this.entityManager.find(PaymentType.class, id);
		logger.info("PaymentType loaded successfully, PaymentType details="+p);
		return p;
	}

	@Override
	public PaymentType findByName(String name) {
		try {
			CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();

			CriteriaQuery cq = cb.createQuery(PaymentType.class);
			Root<PaymentType> roles = cq.from(PaymentType.class);
			cq.where(cb.equal(roles.get("type"), name));

			List<PaymentType> result = this.entityManager.createQuery(cq).getResultList();
			logger.info("Payment type was found by name " + name);
			return result.isEmpty() ? null : result.get(0);
		}catch (Exception e){
			logger.error(e.toString());
			return null;
		}
	}
}
