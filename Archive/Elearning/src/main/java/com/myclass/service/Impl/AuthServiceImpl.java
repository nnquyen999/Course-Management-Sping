package com.myclass.service.Impl;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myclass.dto.LoginDto;
import com.myclass.dto.SessionDto;
import com.myclass.entity.User;
import com.myclass.service.AuthService;
import com.myclass.service.UserService;

@Service
public class AuthServiceImpl implements AuthService{

	@Autowired
	private UserService userService;
	
	@Override
	public SessionDto login(LoginDto loginDto) {
		User user = userService.findByEmail(loginDto.getEmail());
		if(user == null || !BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {
			return storeInfoSession(user);
		}else {
			return storeInfoSession(user);
		}
		
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}
	private SessionDto storeInfoSession(User user) {
		System.out.println(user.getRole().getName());
		SessionDto dto = new SessionDto(user.getId(),user.getFullname(),user.getRole().getName().replace("ROLE_", ""), user.getAvatar(), user.getEmail(), user.getPhone());
		return dto;
	}
}
