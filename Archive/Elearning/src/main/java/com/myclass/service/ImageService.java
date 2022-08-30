package com.myclass.service;


import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	String upload(MultipartFile file) throws Exception;
	void delete(String nameImage);
}
