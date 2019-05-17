package jschool.dao.impl;

import java.util.List;

import jschool.dao.ShippingTypeDAO;
import jschool.model.ShippingType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class ShippingTypeDAOImpl implements ShippingTypeDAO {
	
	private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void add(ShippingType p) {
		p.setIsDeleted(false);
		this.entityManager.persist(p);
		logger.info("ShippingType saved successfully, ShippingType Details="+p);
	}

	@Override
	public void update(ShippingType p) {
		this.entityManager.merge(p);
		logger.info("ShippingType updated successfully, ShippingType Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShippingType> list() {
		List<ShippingType> personsList = this.entityManager.createQuery("FROM " + ShippingType.class.getSimpleName()
				+ " WHERE isDeleted = false").getResultList();
		logger.info("ShippingType List was loaded");
		return personsList;
	}

	@Override
	public ShippingType findById(int id) {
		ShippingType p = this.entityManager.find(ShippingType.class, id);
		logger.info("ShippingType loaded successfully, ShippingType details="+p);
		return p;
	}

	@Override
	public ShippingType find(String type) {
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();

		CriteriaQuery cq = cb.createQuery(ShippingType.class);
		Root<ShippingType> roles = cq.from(ShippingType.class);
		cq.where(cb.equal(roles.get("type"), type));

		List<ShippingType> result = this.entityManager.createQuery(cq).getResultList();
		logger.info("ShippingType was successfully found=");
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public void remove(int id) {
		ShippingType p = this.entityManager.find(ShippingType.class, id);
		if(null != p){
			p.setIsDeleted(true);
			this.entityManager.merge(p);
		}
		logger.info("ShippingType deleted successfully,  details="+p);
	}

}
