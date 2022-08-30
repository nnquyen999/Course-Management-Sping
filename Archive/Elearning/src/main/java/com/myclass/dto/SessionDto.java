package com.myclass.dto;

public class SessionDto {
	private int id;
	private String name;
	private String role;
	private String avatar;
	private String email;
	private String phone;
	public SessionDto() {
		
	}
	public SessionDto(int id, String name, String role, String avatar, String email, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.role = role;
		this.avatar = avatar;
		this.email = email;
		this.phone = phone;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}	
	
}
