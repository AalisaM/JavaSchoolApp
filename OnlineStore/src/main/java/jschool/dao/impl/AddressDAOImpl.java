package jschool.dao.impl;

import jschool.dao.AddressDAO;
import jschool.model.Address;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;

import org.springframework.stereotype.Repository;

@Repository
public class AddressDAOImpl implements AddressDAO {
    private static final Logger logger = Logger.getLogger(ShippingTypeDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Address add(Address p) {
        try {
            this.entityManager.persist(p);
            logger.info("Address saved successfulle=" + p.getAddress() + " " + p.getUsers());
            return p;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Address getById(int id) {
        try {
            Address p = this.entityManager.find(Address.class, id);
            logger.info("Address loaded successfully, Address details=" + p.getAddress());
            return p;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return  null;
    }


    @Override
    public void update(int id, String address) {
        try {
            Address a = this.entityManager.find(Address.class, id);
            if (Objects.isNull(a)) {
                a = new Address();
                a.setAddress(address);
                this.entityManager.persist(a);
                return;
            }
            a.setAddress(address);
            this.entityManager.persist(a);
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }
}
