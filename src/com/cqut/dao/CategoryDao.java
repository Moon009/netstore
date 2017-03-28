package com.cqut.dao;

import java.util.List;

import com.cqut.domain.Category;

public interface CategoryDao {

	void save(Category category);

	List<Category> findAll();

	Category findById(String categoryId);

}
