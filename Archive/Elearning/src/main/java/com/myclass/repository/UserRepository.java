package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.myclass.dto.UserDto;
import com.myclass.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.fullname = ?1, u.email = ?2, u.avatar = ?3, u.phone = ?4, u.roleId = ?5 where u.id = ?6")	
	public void updatePersonalInformation( String fullname, String email, String avatar, String phone, Integer roleId, Integer id);


	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.password = ?1 where u.id = ?2")
	public void updatePassword( String password, Integer id);
	
	@Query("SELECT new com.myclass.dto.UserDto(u.id, u.fullname, u.email, u.phone, r.name) FROM User u JOIN u.role r")
	public List<UserDto> getAllDto();


	public User findByEmail(String email);
} 
