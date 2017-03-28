package com.cqut.service.impl;

import java.util.List;
import java.util.UUID;

import com.cqut.commons.Page;
import com.cqut.dao.BookDao;
import com.cqut.dao.CategoryDao;
import com.cqut.dao.CustomerDao;
import com.cqut.dao.OrderDao;
import com.cqut.dao.impl.BookDaoImpl;
import com.cqut.dao.impl.CategoryDaoImpl;
import com.cqut.dao.impl.CustomerDaoImpl;
import com.cqut.dao.impl.OrderDaoImpl;
import com.cqut.domain.Book;
import com.cqut.domain.Category;
import com.cqut.domain.Customer;
import com.cqut.domain.Order;
import com.cqut.service.BusinessService;

public class BusinessServiceImpl implements BusinessService {
	private CategoryDao categoryDao = new CategoryDaoImpl();
	private BookDao bookDao = new BookDaoImpl();
	private CustomerDao customerDao = new CustomerDaoImpl();
	private OrderDao orderDao = new OrderDaoImpl();
	public void addCategory(Category category) {
		category.setId(UUID.randomUUID().toString());
		categoryDao.save(category);
	}

	public List<Category> findAllCategories() {
		return categoryDao.findAll();
	}

	public Category findCategoryById(String categoryId) {
		return categoryDao.findById(categoryId);
	}

	public void addBook(Book book) {
		book.setId(UUID.randomUUID().toString());
		bookDao.save(book);
	}

	public Page findAllBookPageRecords(String pagenum) {
		
		int currentPageNum = 1;
		if(pagenum!=null){
			currentPageNum = Integer.parseInt(pagenum);
		}
		int totalRecords = bookDao.findAllBooksNumber();
		Page page = new Page(currentPageNum, totalRecords);
		
		page.setRecords(bookDao.findPageBooks(page.getStartIndex(), page.getPageSize()));
		
		return page;
	}

	public Book findBookById(String bookId) {
		return bookDao.findById(bookId);
	}

	public Page findAllBookPageRecords(String pagenum, String categoryId) {
		int currentPageNum = 1;
		if(pagenum!=null){
			currentPageNum = Integer.parseInt(pagenum);
		}
		int totalRecords = bookDao.findCategoryBooksNumber(categoryId);
		Page page = new Page(currentPageNum, totalRecords);
		
		page.setRecords(bookDao.findPageBooks(page.getStartIndex(), page.getPageSize(),categoryId));
		
		return page;
	}

	public void regitsCustomer(Customer c) {
		c.setId(UUID.randomUUID().toString());
		customerDao.save(c);
	}

	public void activeCustomer(String code) {
		Customer c = customerDao.findByCode(code);
		c.setActived(true);
		customerDao.update(c);
	}

	public Customer customerLogin(String username, String password) {
		Customer c = customerDao.findCustomer(username,password);
		if(c==null)
			return null;
		if(!c.isActived())
			return null;
		return c;
	}

	public void genOrder(Order order) {
		if(order.getCustomer()==null)
			throw new IllegalArgumentException("订单的客户不能为空");
		order.setId(UUID.randomUUID().toString());
		orderDao.save(order);
	}

	public void updateOrder(Order order) {
		orderDao.update(order);
	}

	public Order findOrderById(String orderId) {
		return orderDao.findById(orderId);
	}

	public Order findOrderByOrderNum(String orderNum) {
		return orderDao.findByOrderNum(orderNum);
	}

	public List<Order> findOrdersByCustomer(Customer c) {
		return orderDao.findOrdersByCustomerId(c.getId());
	}

}
