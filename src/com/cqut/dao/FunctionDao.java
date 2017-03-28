package com.cqut.dao;

import java.util.List;

import com.cqut.permission.domain.Function;
import com.cqut.permission.domain.Role;





public interface FunctionDao {

	List<Function> findFunctions(Role role);

}
