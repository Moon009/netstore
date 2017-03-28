package com.cqut.dao;

import java.util.List;

import com.cqut.permission.domain.Role;
import com.cqut.permission.domain.User;


public interface RoleDao {

	List<Role> findRoles(User user);

}
