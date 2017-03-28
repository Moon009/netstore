package com.cqut.service;

import java.util.List;

import com.cqut.permission.domain.Function;
import com.cqut.permission.domain.Role;
import com.cqut.permission.domain.User;



public interface PrivilegeService {
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 */
	User login(String username,String password);
	/**
	 * 根据用户查询拥有的角色
	 * @param user
	 * @return
	 */
	List<Role> findRoles(User user);
	/**
	 * 根据角色查询功能
	 * @param role
	 * @return
	 */
	List<Function> findFunctions(Role role);
	
}
