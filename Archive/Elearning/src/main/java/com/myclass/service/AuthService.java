package com.myclass.service;

import com.myclass.dto.LoginDto;
import com.myclass.dto.SessionDto;

public interface AuthService {
	SessionDto login(LoginDto loginDto);
	void logout();
}
