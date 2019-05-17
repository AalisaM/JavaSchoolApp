package jschool.dao;

import jschool.model.PaymentStatus;

public interface PaymentStatusDAO {
	 PaymentStatus findById(int id);
	 PaymentStatus getByStatus(String status);
}
