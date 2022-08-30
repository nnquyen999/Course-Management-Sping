package com.myclass.dto;

public class TargetDto {
	private int id;
	private String title;
	private int courseId;
	private String courseTitle;
	public TargetDto() {
		
	}
	public TargetDto(int id, String title, int courseId, String courseTitle) {
		super();
		this.id = id;
		this.title = title;
		this.courseId = courseId;
		this.courseTitle = courseTitle;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	
}
