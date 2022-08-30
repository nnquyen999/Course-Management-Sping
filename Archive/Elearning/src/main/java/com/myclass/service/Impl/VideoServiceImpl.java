package com.myclass.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myclass.entity.Course;
import com.myclass.entity.Video;
import com.myclass.repository.VideoRepository;
import com.myclass.service.CourseService;
import com.myclass.service.VideoService;

@Service
public class VideoServiceImpl  implements VideoService{
	@Autowired
	VideoRepository videoRepository;
	@Autowired
	CourseService courseService;
	
	@Override
	public Video getById(int id) {		
		return videoRepository.findById(id).get();
	}

	@Override
	public void add(Video video) {
		Course course = courseService.getById(video.getCourseId());
		course.setLeturesCount(course.getLeturesCount()+1);
		course.setHourCount(course.getHourCount()+video.getTimeCount());
		videoRepository.save(video);	
	}

	@Override
	public void deleteById(int id) {
		videoRepository.deleteById(id);
	}

	@Override
	public List<Video> getAll() {
		return videoRepository.findAll();
	}

	@Override
	public void update(Video video) {
		Video oldVideo = videoRepository.findById(video.getId()).get();
		
		if(oldVideo.getCourseId() != video.getCourseId()) {
			Course fromCourse = courseService.getById(oldVideo.getCourseId());
			Course toCourse = courseService.getById(video.getCourseId());
			
			fromCourse.setLeturesCount(fromCourse.getLeturesCount()-1);
			fromCourse.setHourCount(fromCourse.getHourCount() - oldVideo.getTimeCount());
			
			toCourse.setLeturesCount(toCourse.getLeturesCount()+1);
			toCourse.setHourCount(toCourse.getHourCount() + video.getTimeCount());
			
		}else {
			Course courseNeedChange = courseService.getById(video.getCourseId());
			courseNeedChange.setHourCount(courseNeedChange.getHourCount()-oldVideo.getTimeCount()+video.getTimeCount());
		}
		
		videoRepository.save(video);		
	}

}
