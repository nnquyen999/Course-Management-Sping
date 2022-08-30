package com.myclass.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.myclass.entity.Category;

public interface CategoryService {
	List<Category> getAll();
	Category getById(int id);
	void add(Category category);
	void update(Category category);
	void deleteById(int id) throws DataIntegrityViolationException ;
	
}
