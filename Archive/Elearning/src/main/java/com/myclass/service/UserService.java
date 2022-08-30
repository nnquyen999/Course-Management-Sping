package com.myclass.service;

import java.util.List;


import com.myclass.dto.UserDto;
import com.myclass.entity.User;

public interface UserService {
	User getById(int id);
	void add(User user);
	void updateInformation(User user);
	void deleteById(int id);
	void updatePassword(User user);	
	List<UserDto> getAllDto();
	User findByEmail(String email);
}
