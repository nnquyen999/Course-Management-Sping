package com.myclass.service.Impl;

import java.util.List;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myclass.dto.UserDto;
import com.myclass.entity.User;
import com.myclass.repository.UserRepository;
import com.myclass.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public List<UserDto> getAllDto() {
		return userRepository.getAllDto();
	}

	@Override
	public User getById(int id) {
		return userRepository.findById(id).get();
	}

	@Override
	public void add(User user) {
		hashPassword(user);
		userRepository.save(user);
		
	}

	@Override
	public void updateInformation(User user) {
		userRepository.updatePersonalInformation(user.getFullname(), user.getEmail(), user.getAvatar(), user.getPhone(),
				user.getRoleId(), user.getId());
	}

	@Override
	public void deleteById(int id) {
		userRepository.deleteById(id);

	}

	@Override
	public void updatePassword(User user) {
		hashPassword(user);
		System.out.println(user.getPassword() + " " + user.getId());
		userRepository.updatePassword(user.getPassword(), user.getId());
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);		
	}

	// -----------------------------------PRIVATE
	// METHOD------------------------------------------------------//
	private void hashPassword(User user) {
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashed);
	}

}
