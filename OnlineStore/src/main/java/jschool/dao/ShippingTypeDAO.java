package jschool.dao;

import java.util.List;

import jschool.model.ShippingType;

public interface ShippingTypeDAO {
	List<ShippingType> list();
	ShippingType findById(int id);
	void update(ShippingType p);
	void add(ShippingType p);
	void remove(int id);

	ShippingType find(String type);
}
