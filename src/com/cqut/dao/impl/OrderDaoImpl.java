package com.cqut.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.cqut.dao.OrderDao;
import com.cqut.domain.Order;
import com.cqut.domain.OrderItem;
import com.cqut.exception.DaoException;
import com.cqut.util.DBCPUtil;

public class OrderDaoImpl implements OrderDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	public void save(Order order) {
		try {
			//保存订单的基本信息
			qr.update("insert into orders (id,orderNum,quantity,amount,status,customersId) values(?,?,?,?,?,?)", 
					order.getId(),
					order.getOrderNum(),
					order.getQuantity(),
					order.getAmount(),
					order.getStatus(),
					order.getCustomer().getId());
			//有没有订单项：有的话也保存（级联保存）
			List<OrderItem> items = order.getItems();
			if(items.size()>0){
				for(OrderItem item:items){
					qr.update("insert into orders_item (id,quantity,price,booksId,ordersId) values(?,?,?,?,?)", 
							item.getId(),
							item.getQuantity(),
							item.getPrice(),
							item.getBook().getId(),
							order.getId()
							);
				}
			}
				
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public void update(Order order) {
		try{
			qr.update("update orders set orderNum=?,quantity=?,amount=?,status=? where id=?", 
					order.getOrderNum(),
					order.getQuantity(),
					order.getAmount(),
					order.getStatus(),
					order.getId());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public Order findById(String orderId) {
		try {
			return qr.query("select * from orders where id=?", new BeanHandler<Order>(Order.class),orderId);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public Order findByOrderNum(String orderNum) {
		try {
			return qr.query("select * from orders where orderNum=?", new BeanHandler<Order>(Order.class),orderNum);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public List<Order> findOrdersByCustomerId(String customerId) {
		try {
			return qr.query("select * from orders where customersId=? order by orderNum desc", new BeanListHandler<Order>(Order.class),customerId);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

}
