package jschool.dao.impl;

import jschool.dao.RoleDAO;
import jschool.model.Role;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {
    private static final Logger logger = Logger.getLogger(RoleDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getByRole(String r) {
        try {
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery(Role.class);
            Root<Role> roles = cq.from(Role.class);
            cq.where(cb.equal(roles.get("role"), r));
            List<Role> result = this.entityManager.createQuery(cq).getResultList();
            logger.info("Role was found");
            return result.isEmpty() ? null : result.get(0);
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }

}
