package com.myclass.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.myclass.entity.Role;

public interface RoleService {
	List<Role> getAll();
	Role getById(int id);
	void add(Role role);
	void update(Role role);
	void deleteById(int id) throws DataIntegrityViolationException;
	int getIdByName(String name);
}
