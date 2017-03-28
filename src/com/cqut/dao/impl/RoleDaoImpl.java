package com.cqut.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.cqut.dao.RoleDao;
import com.cqut.exception.DaoException;
import com.cqut.permission.domain.Role;
import com.cqut.permission.domain.User;
import com.cqut.util.DBCPUtil;

public class RoleDaoImpl implements RoleDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	public List<Role> findRoles(User user) {
		try {
			return qr.query("select r.* from roles r,users_roles ur where ur.r_id=r.id and ur.u_id=?", 
					new BeanListHandler<Role>(Role.class),user.getId());
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

}
