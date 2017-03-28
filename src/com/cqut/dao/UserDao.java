package com.cqut.dao;

import com.cqut.permission.domain.User;

public interface UserDao {

	User find(String username, String password);

}
