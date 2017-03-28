package com.cqut.service;

import java.util.List;

import com.cqut.permission.domain.Function;
import com.cqut.permission.domain.Role;
import com.cqut.permission.domain.User;



public interface PrivilegeService {
	/**
	 * �û���¼
	 * @param username
	 * @param password
	 */
	User login(String username,String password);
	/**
	 * �����û���ѯӵ�еĽ�ɫ
	 * @param user
	 * @return
	 */
	List<Role> findRoles(User user);
	/**
	 * ���ݽ�ɫ��ѯ����
	 * @param role
	 * @return
	 */
	List<Function> findFunctions(Role role);
	
}
