package com.cqut.service;

import java.util.List;

import com.cqut.commons.Page;
import com.cqut.domain.Book;
import com.cqut.domain.Category;
import com.cqut.domain.Customer;
import com.cqut.domain.Order;



public interface BusinessService {
	/**
	 * 添加一个分类
	 * @param category
	 */
	void addCategory(Category category);
	/**
	 * 查询所有分类
	 * @return
	 */
	List<Category> findAllCategories();
	/**
	 * 根据主键查询一个分类
	 * @param categoryId
	 * @return 没有找到返回null
	 */
	Category findCategoryById(String categoryId);
	/**
	 * 添加一本图书
	 * @param book
	 */
	void addBook(Book book);
	/**
	 * 根据用户要查看的页码，返回封装了分页信息的Page对象
	 * @param pagenum 默认为1
	 * @return
	 */
	Page findAllBookPageRecords(String pagenum);
	/**
	 * 根据用户要查看的页码，返回封装了分页信息的Page对象,按照分类进行查询的
	 * @param pagenum 默认为1
	 * @return
	 */
	Page findAllBookPageRecords(String pagenum,String categoryId);
	/**
	 * 根据id查询一本书
	 * @param bookId
	 * @return
	 */
	Book findBookById(String bookId);
	/**
	 * 新客户注册
	 * @param c
	 * @return
	 */
	void regitsCustomer(Customer c);
	/**
	 * 根据激活码激活账户
	 * @param code
	 */
	void activeCustomer(String code);
	/**
	 * 客户登录
	 * @param username
	 * @param password
	 * @return 如果用户名或密码不正确，或者没有激活，都返回null
	 */
	Customer customerLogin(String username,String password);
	/**
	 * 生成订单
	 * @param order
	 */
	void genOrder(Order order);
	/**
	 * 更新订单
	 * @param orderId
	 */
	void updateOrder(Order order);
	/**
	 * 根据订单id查询订单
	 * @param orderId
	 * @return
	 */
	Order findOrderById(String orderId);
	/**
	 * 根据订单号查询订单
	 * @param orderNum
	 * @return
	 */
	Order findOrderByOrderNum(String orderNum);
	/**
	 * 查询客户的订单
	 * @param c
	 * @return
	 */
	List<Order> findOrdersByCustomer(Customer c);
}
