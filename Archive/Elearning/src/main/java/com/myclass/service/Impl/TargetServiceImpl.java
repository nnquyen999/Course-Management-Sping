package com.myclass.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myclass.dto.TargetDto;
import com.myclass.entity.Target;
import com.myclass.repository.TargetRepository;
import com.myclass.service.TargetService;

@Service
public class TargetServiceImpl implements TargetService{
	@Autowired
	TargetRepository targetRepository;
	
	@Override
	public List<TargetDto> getAllDto() {		
		return targetRepository.getAllDto();
	}
	@Override
	public List<Target> getAll() {		
		return targetRepository.findAll();
	}
	
	@Override
	public Target getById(int id) {
		
		return targetRepository.findById(id).get();
	}

	@Override
	public void add(Target target) {
		targetRepository.save(target);
		
	}

	@Override
	public void update(Target target) {
		targetRepository.save(target);
		
	}

	@Override
	public void deleteById(int id){
		targetRepository.deleteById(id);
		
	}
}
