package com.myclass.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.myclass.entity.Course;

public interface CourseService {
	Course getById(int id);
	void add(Course course);
	void update(Course course);
	void deleteById(int id) throws DataIntegrityViolationException ;
	List<Course> getAll();
}
