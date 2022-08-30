package com.myclass.service;

import java.util.List;

import com.myclass.entity.Video;

public interface VideoService {
	Video getById(int id);
	void add(Video video);
	void deleteById(int id);
	List<Video> getAll();
	void update(Video video);
}
