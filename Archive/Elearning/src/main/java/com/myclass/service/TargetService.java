package com.myclass.service;

import java.util.List;


import com.myclass.dto.TargetDto;
import com.myclass.entity.Target;

public interface TargetService{
	List<Target> getAll();
	Target getById(int id);
	void add(Target target);
	void update(Target target);
	void deleteById(int id);
	List<TargetDto> getAllDto();
}
