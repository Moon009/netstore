package com.cqut.service.impl;

import java.util.List;

import com.cqut.dao.FunctionDao;
import com.cqut.dao.RoleDao;
import com.cqut.dao.UserDao;
import com.cqut.dao.impl.FunctionDaoImpl;
import com.cqut.dao.impl.RoleDaoImpl;
import com.cqut.dao.impl.UserDaoImpl;
import com.cqut.permission.domain.Function;
import com.cqut.permission.domain.Role;
import com.cqut.permission.domain.User;
import com.cqut.service.PrivilegeService;

public class PrivilegeServiceImpl implements PrivilegeService {
	private UserDao userDao = new UserDaoImpl();
	private RoleDao roleDao = new RoleDaoImpl();
	private FunctionDao functionDao = new FunctionDaoImpl();
	public User login(String username, String password) {
		return userDao.find(username,password);
	}

	public List<Function> findFunctions(Role role) {
		return functionDao.findFunctions(role);
	}

	public List<Role> findRoles(User user) {
		return roleDao.findRoles(user);
	}

}
