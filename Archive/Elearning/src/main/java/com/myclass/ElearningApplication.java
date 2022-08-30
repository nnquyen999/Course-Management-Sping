package com.myclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.myclass.entity.Role;
import com.myclass.entity.User;
import com.myclass.service.RoleService;
import com.myclass.service.UserService;

@SpringBootApplication
public class ElearningApplication {
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(ElearningApplication.class, args);
	}
	@Bean
	ApplicationRunner init() {
		return (ApplicationArguments args) -> dataSetup();
	}
	public void dataSetup() {
		if(roleService.getAll().isEmpty()) {
			roleService.add(new Role("ROLE_ADMIN","Quản Trị Viên"));
			roleService.add(new Role("ROLE_TEACHER","Giáo Viên"));
			roleService.add(new Role("ROLE_USER","Học Viên"));
		}	
		if(userService.getAllDto().isEmpty()) {
			userService.add(new User("admin@mail.com", "This is Admin", "123456", "/upload/admin.jpg", "0909090909",roleService.getIdByName("ROLE_ADMIN")));
		}
	}
}
