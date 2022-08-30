package com.myclass.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.myclass.entity.Role;
import com.myclass.repository.RoleRepository;
import com.myclass.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public List<Role> getAll() {
		return roleRepository.findAll();
	}

	@Override
	public Role getById(int id) {
		return roleRepository.findById(id).get();
	}

	@Override
	public void add(Role role) {
		roleRepository.save(role);
		
	}

	@Override
	public void update(Role role) {
		roleRepository.save(role);		
	}

	@Override
	public void deleteById(int id) throws DataIntegrityViolationException {
		roleRepository.deleteById(id);
		
	}
	@Override
	public int getIdByName(String name) {
		return roleRepository.findByName(name).getId();
	}
}
