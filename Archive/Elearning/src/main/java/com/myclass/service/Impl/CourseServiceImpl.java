package com.myclass.service.Impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.myclass.entity.Course;
import com.myclass.repository.CourseRepository;
import com.myclass.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {
	@Autowired
	CourseRepository courseRepository;
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	@Override
	public Course getById(int id) {
		return courseRepository.findById(id).get();
	}

	@Override
	public void add(Course course) {
		course.setPromotionPrice((course.getPrice()-(course.getPrice()*course.getDiscount()/100)));
		course.setLastUpdate(LocalDateTime.now());
		courseRepository.save(course);	
	}

	@Override
	public void update(Course course) {
		course.setPromotionPrice((course.getPrice()-(course.getPrice()*course.getDiscount()/100)));
		System.out.println(course.getPromotionPrice());
		course.setLastUpdate(LocalDateTime.now());
		courseRepository.save(course);
		
	}

	@Override
	public void deleteById(int id) throws DataIntegrityViolationException {
		Course course = getById(id);
		courseRepository.delete(course);
	}

	@Override
	public List<Course> getAll() {
		return courseRepository.findAll();
	}
	
}
