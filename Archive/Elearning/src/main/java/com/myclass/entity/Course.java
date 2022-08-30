package com.myclass.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="course")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@NotBlank(message = "Vui lòng nhập tiêu đề!")
	@Column(name = "tile")
	private String title;
	
	@Column(name = "image")
	private String image;
		
	@Column(name = "letures_count")
	private int leturesCount;
	
	@Column(name = "hour_count")
	private int hourCount;
	
	@Column(name = "view_count")
	private int viewCount;
	
	@PositiveOrZero(message = "Vui lòng nhập học phí!")
	@Column(name = "price" )
	private double price;
	
	@Column(name = "discount")
	@Max(value = 100, message="Số phần trăm không chính xác")
	private int discount;
	
	@Column(name = "promotion_price")
	@PositiveOrZero(message = "Vui lòng nhập khuyến mãi!")
	private double promotionPrice;
	
	@NotBlank(message = "Vui lòng nhập mô tả!")
	@Column(name = "description")
	private String description;
	
	@Column(name = "content")
	private String content;
	
	@PositiveOrZero(message = "Vui lòng chọn khóa học!")
	@Column(name = "category_id")
	private int categoryId;
	
	@Column(name = "last_update")
	@UpdateTimestamp	
	private LocalDateTime lastUpdate;
	
	
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	private List<UserCourse> userCourses; 
	
	@ManyToOne
	@JoinColumn(name = "category_id", updatable = false, insertable = false )
	private Category category;

	@OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
	private List<Video> videos;
	
	@OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
	private List<Target> targets;
	
	
	public Course() {
		
	}

	public Course(int id, String title, String image, int leturesCount, int hourCount, int viewCount, double price,
			int discount, String description, String content, int categoryId) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.leturesCount = leturesCount;
		this.hourCount = hourCount;
		this.viewCount = viewCount;
		this.price = price;
		this.discount = discount;
		this.promotionPrice = (price*discount)/100;
		this.description = description;
		this.content = content;
		this.categoryId = categoryId;
		this.lastUpdate = LocalDateTime.now();
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


	public List<Video> getVideos() {
		return videos;
	}


	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public int getLeturesCount() {
		return leturesCount;
	}


	public void setLeturesCount(int leturesCount) {
		this.leturesCount = leturesCount;
	}


	public int getHourCount() {
		return hourCount;
	}


	public void setHourCount(int hourCount) {
		this.hourCount = hourCount;
	}


	public int getViewCount() {
		return viewCount;
	}


	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getDiscount() {
		return discount;
	}


	public void setDiscount(int discount) {
		this.discount = discount;
	}


	public double getPromotionPrice() {
		return promotionPrice;
	}


	public void setPromotionPrice(double promotionPrice) {
		this.promotionPrice = promotionPrice;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}


	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}


	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	public List<UserCourse> getUserCourses() {
		return userCourses;
	}


	public void setUserCourses(List<UserCourse> userCourses) {
		this.userCourses = userCourses;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	
}
