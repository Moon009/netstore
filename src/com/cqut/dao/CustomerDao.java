package com.cqut.dao;

import com.cqut.domain.Customer;

public interface CustomerDao {

	void save(Customer c);

	Customer findByCode(String code);

	void update(Customer c);

	Customer findCustomer(String username, String password);
	
	Customer findById(String customerId);

}
