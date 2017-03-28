package com.cqut.commons;

import com.cqut.domain.Category;
import com.cqut.service.BusinessService;
import com.cqut.service.impl.BusinessServiceImpl;


public class MyFunctions {
	private static BusinessService s = new BusinessServiceImpl();
	public static String showCategoryName(String categoryId){
		Category c = s.findCategoryById(categoryId);
		if(c!=null){
			return c.getName();
		}
		return null;
	}
}
