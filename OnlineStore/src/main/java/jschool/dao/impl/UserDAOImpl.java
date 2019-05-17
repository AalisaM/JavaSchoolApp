package jschool.dao.impl;

import jschool.dao.UserDAO;
import jschool.model.Address;
import jschool.model.Role;
import jschool.model.User;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class UserDAOImpl implements UserDAO {
    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> list() {
        try {
            List<User> personsList = this.entityManager.createQuery("FROM " + User.class.getSimpleName()).getResultList();
            logger.info("user list loaded");
            return personsList;
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public User findById(int id) {
        try{
            User p = this.entityManager.find(User.class, id);
            logger.info("User loaded successfully, User details="+p.getFullName());
            return p;
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try{
            CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();

            CriteriaQuery cq = cb.createQuery(User.class);
            Root<User> roles = cq.from(User.class);
            cq.where(cb.equal(roles.get("email"), email));

            List<User> result = this.entityManager.createQuery(cq).getResultList();
            logger.info("User loaded successfully");
            return result.isEmpty() ? null : result.get(0);
        }catch (Exception e){
            logger.error(e.toString());
            return null;
        }
    }

    @Override
    public boolean isUserAdmin(int id) {
        return false;
    }

    @Override
    public void add(User p) {
        try {
            this.entityManager.persist(p);
            logger.info("User saved successfully, User Details=" + p.getFullName());
        }catch (Exception e){
            logger.error(e.toString());
        }
    }

    @Override
    public void update(User p) {
        try{
            User old = this.entityManager.find(User.class, p.getId());
            old.setFullName(p.getFullName());
            old.setEmail(p.getEmail());
            old.setBirth(p.getBirth());
            old.setPassword(p.getPassword());
            old.setPhone(p.getPhone());
            this.entityManager.merge(old);
            logger.info("User updated successfully, User Details="+old);
        }catch (Exception e){
            logger.error(e.toString());
        }
    }

    @Override
    public void updateAdminStatus(User p){
        try{
            User old = this.entityManager.find(User.class, p.getId());
            Role role = this.entityManager.find(Role.class, 2);
            old.setAdmin(p.isAdmin());
            if (p.isAdmin()){
                old.getRoles().add(role);
            }else{
                old.getRoles().remove(role);
            }
            this.entityManager.merge(old);
            logger.info("User admin status was changed");
        }catch (Exception e){
            logger.error(e.toString());
        }
    }

    @Override
    public void updatePassword(User p){
        try {
            this.entityManager.merge(p);
            logger.info("User password was successfully changed");
        }catch (Exception e){
            logger.error(e.toString());
        }
    }
    @Override
    public void remove(int id) {
        try {
            User p = this.entityManager.find(User.class, id);
            if (!Objects.isNull(p)){
                this.entityManager.remove(p);
            }
            logger.info("User deleted successfully, User details="+p);
        }catch (Exception e){
            logger.error(e.toString());
        }
    }

    @Override
    public void setActiveAddressToUser(int idAddr, int idUser) {
        try {
            User user = this.entityManager.find(User.class, idUser);
            Address address = this.entityManager.find(Address.class, idAddr);
            user.setActiveAddressId(address);
            this.entityManager.persist(user);
            logger.info("New active address was set to user");
        }catch (Exception e){
            logger.error(e.toString());
        }
    }

    @Override
    public void addAddressById(int idAddr, int idUser) {
    }

    @Override
    public void removeAddressById(int idAddr, int idUser) {
        try {
            Address address = this.entityManager.find(Address.class, idAddr);
            User user = this.entityManager.find(User.class, idUser);
            user.getAddresses().remove(address);
            if (user.getActiveAddressId().getId() == idAddr) {
                user.setActiveAddressId(null);
            }
            this.entityManager.persist(user);
            logger.info("Address " + idAddr + "was removed from user address list");
        }catch (Exception e){
            logger.error(e.toString());
        }
    }

    @Override
    public void addAddress(User p, Address a) {
        try{
            User user = this.entityManager.find(User.class, p.getId());
            Address address = this.entityManager.find(Address.class, a.getId());
            user.getAddresses().add(address);
            this.entityManager.persist(user);
            logger.info("User added address successfully, User details="+ (this.entityManager.find(User.class, p.getId())).toString());
        }
        catch(Exception e){
            logger.error(e.toString());
        }
    }
}
