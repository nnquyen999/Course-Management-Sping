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
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "videos")
public class Video {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotBlank(message = "Vui lòng nhập tiêu đề!")
	@Column(name = "title")
	private String title;
	
	@NotBlank(message = "Vui lòng nhập địa chỉ video!")
	@Column(name = "url")
	private String url;	
	
	@PositiveOrZero(message = "Thời gian không chính xác")
	@Column(name = "time_count")
	private int timeCount;	
	
	@PositiveOrZero(message = "Khóa học không chính xác")
	@Column(name = "course_id")
	private int courseId;	
	
	@Column(name = "image")
	private String image;
	
	
	@ManyToOne
	@JoinColumn(name = "course_id",  insertable = false, updatable = false)
	private Course course;
	
	
	public Video() {
	
	}

	public Video(int id, String title, String url, int timeCount, int courseId) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
		this.timeCount = timeCount;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTimeCount() {
		return timeCount;
	}

	public void setTimeCount(int timeCount) {
		this.timeCount = timeCount;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}


	
	
}
