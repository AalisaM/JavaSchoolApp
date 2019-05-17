package jschool.dao;

import jschool.model.PaymentType;
import java.util.List;

public interface PaymentTypeDAO {
	 List<PaymentType> list();
	 PaymentType findById(int id);
	 PaymentType findByName(String name);
}
