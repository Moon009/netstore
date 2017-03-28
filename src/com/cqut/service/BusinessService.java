package com.cqut.service;

import java.util.List;

import com.cqut.commons.Page;
import com.cqut.domain.Book;
import com.cqut.domain.Category;
import com.cqut.domain.Customer;
import com.cqut.domain.Order;



public interface BusinessService {
	/**
	 * ���һ������
	 * @param category
	 */
	void addCategory(Category category);
	/**
	 * ��ѯ���з���
	 * @return
	 */
	List<Category> findAllCategories();
	/**
	 * ����������ѯһ������
	 * @param categoryId
	 * @return û���ҵ�����null
	 */
	Category findCategoryById(String categoryId);
	/**
	 * ���һ��ͼ��
	 * @param book
	 */
	void addBook(Book book);
	/**
	 * �����û�Ҫ�鿴��ҳ�룬���ط�װ�˷�ҳ��Ϣ��Page����
	 * @param pagenum Ĭ��Ϊ1
	 * @return
	 */
	Page findAllBookPageRecords(String pagenum);
	/**
	 * �����û�Ҫ�鿴��ҳ�룬���ط�װ�˷�ҳ��Ϣ��Page����,���շ�����в�ѯ��
	 * @param pagenum Ĭ��Ϊ1
	 * @return
	 */
	Page findAllBookPageRecords(String pagenum,String categoryId);
	/**
	 * ����id��ѯһ����
	 * @param bookId
	 * @return
	 */
	Book findBookById(String bookId);
	/**
	 * �¿ͻ�ע��
	 * @param c
	 * @return
	 */
	void regitsCustomer(Customer c);
	/**
	 * ���ݼ����뼤���˻�
	 * @param code
	 */
	void activeCustomer(String code);
	/**
	 * �ͻ���¼
	 * @param username
	 * @param password
	 * @return ����û��������벻��ȷ������û�м��������null
	 */
	Customer customerLogin(String username,String password);
	/**
	 * ���ɶ���
	 * @param order
	 */
	void genOrder(Order order);
	/**
	 * ���¶���
	 * @param orderId
	 */
	void updateOrder(Order order);
	/**
	 * ���ݶ���id��ѯ����
	 * @param orderId
	 * @return
	 */
	Order findOrderById(String orderId);
	/**
	 * ���ݶ����Ų�ѯ����
	 * @param orderNum
	 * @return
	 */
	Order findOrderByOrderNum(String orderNum);
	/**
	 * ��ѯ�ͻ��Ķ���
	 * @param c
	 * @return
	 */
	List<Order> findOrdersByCustomer(Customer c);
}
