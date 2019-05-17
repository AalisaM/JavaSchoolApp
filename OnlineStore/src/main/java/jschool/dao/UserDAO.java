package jschool.dao;

import jschool.model.Address;
import jschool.model.User;

import java.util.List;

public interface UserDAO {
    List<User> list();
    User findById(int id);
    User findByEmail(String email);
    boolean isUserAdmin(int id);
    void add(User p);
    void update(User p);
    void updateAdminStatus(User p);
    void updatePassword(User p);
    void remove(int id);

    void setActiveAddressToUser(int idAddr, int idUser);
    void addAddressById(int idAddr, int idUser);
    void removeAddressById(int idAddr, int idUser);
    void addAddress(User p, Address a);
}
