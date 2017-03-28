package com.cqut.dao;

import java.util.List;

import com.cqut.domain.Order;


public interface OrderDao {

	void save(Order order);

	void update(Order order);

	Order findById(String orderId);

	Order findByOrderNum(String orderNum);
	/**
	 * ���ն����� ��������
	 * @param id
	 * @return
	 */
	List<Order> findOrdersByCustomerId(String customerId);

}
