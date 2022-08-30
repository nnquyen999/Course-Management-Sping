package com.myclass.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.myclass.entity.Category;
import com.myclass.repository.CategoryRepository;
import com.myclass.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	@Override
	public List<Category> getAll() {
		List<Category> categories = categoryRepository.findAll();
		return categories;
	}

	@Override
	public Category getById(int id) {
		return categoryRepository.findById(id).get();
	}

	@Override
	public void add(Category category) {
		categoryRepository.save(category);
	}

	@Override
	public void update(Category category) {
		categoryRepository.save(category);		
	}

	@Override
	public void deleteById(int id) throws DataIntegrityViolationException {
		categoryRepository.deleteById(id);	
	}

}
