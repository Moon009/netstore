package com.cqut.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.cqut.dao.FunctionDao;
import com.cqut.exception.DaoException;
import com.cqut.permission.domain.Function;
import com.cqut.permission.domain.Role;
import com.cqut.util.DBCPUtil;



public class FunctionDaoImpl implements FunctionDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	public List<Function> findFunctions(Role role) {
		try {
			return qr.query("select f.* from functions f,roles_functions rf where f.id=rf.f_id and rf.r_id=?", 
					new BeanListHandler<Function>(Function.class),role.getId());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	

}
