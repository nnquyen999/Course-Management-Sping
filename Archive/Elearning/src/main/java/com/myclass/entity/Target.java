package com.myclass.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "targets")
public class Target {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotBlank(message = "Vui lòng nhập tiêu đề!")
	@Column(name = "title")
	private String title;
	
	@NotNull(message = "Vui lòng chọn khóa học!")
	@PositiveOrZero(message = "Khóa học không hợp lệ!")
	@Column(name = "course_id")
	private int courseId;
	
	@ManyToOne
	@JoinColumn(name = "course_id",  insertable = false, updatable = false)
	private Course course;
	
	public Target() {
	
	}

	public Target(int id, String title, int courseId) {
		super();
		this.id = id;
		this.title = title;
		this.courseId = courseId;
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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	
}
