package com.cqut.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.cqut.dao.UserDao;
import com.cqut.exception.DaoException;
import com.cqut.permission.domain.User;
import com.cqut.util.DBCPUtil;

public class UserDaoImpl implements UserDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	public User find(String username, String password) {
		try {
			return qr.query("select * from users where username=? and password=?", new BeanHandler<User>(User.class),username,password);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
}
