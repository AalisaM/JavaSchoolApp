package jschool.dao;

import jschool.model.Address;


public interface AddressDAO {
    Address add(Address p);
    Address getById(int id);
    void update(int id, String address);
}
