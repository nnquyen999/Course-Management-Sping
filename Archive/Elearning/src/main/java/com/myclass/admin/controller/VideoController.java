package com.myclass.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myclass.entity.Course;
import com.myclass.entity.Video;
import com.myclass.service.CourseService;
import com.myclass.service.ImageService;
import com.myclass.service.VideoService;

@Controller
@RequestMapping("${adress.video}")
public class VideoController {
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private ImageService imageService;

	@Autowired
	private VideoService videoService;
	
	@Value("${path.video}")
	private String videoPath;	
	@Value("${adress.video}")
	private String videoAdress;
	
	@Value("${path.video-add}")
	private String addVideoPath;
	
	@Value("${path.video-edit}")
	private String editVideoPath;
	
	@Value("${adress.406}")
	private String page406;
	
	@Value("${adress.404}")
	private String adress404;
	
	@RequestMapping(value= "", method= RequestMethod.GET)
	public String index(Model model) {
		List<Video> videos = videoService.getAll();
		model.addAttribute("videos", videos);
		return videoPath;
	}
	@RequestMapping(value= "add", method= RequestMethod.GET)
	public String add(Model model) {
		List<Course> courses = courseService.getAll();
		if(courses.isEmpty()) {
			return "redirect:"+ page406 + "?id=course";
		}
		model.addAttribute("courses",courses);
		model.addAttribute("video", new Video());
		
		return addVideoPath;
	}
	@RequestMapping(value= "add", method= RequestMethod.POST)
	public String add(@Valid @ModelAttribute Video video, BindingResult errors, Model model, @RequestParam("fileUpload") MultipartFile file ) {
		if(!file.isEmpty()) {
			try {
				video.setImage(imageService.upload(file));		
			}catch(Exception e) {
				errors.rejectValue("image", "errors.course", e.toString());
			}
		}
		if(errors.hasErrors()) {
			List<Course> courses = courseService.getAll();
			model.addAttribute("courses",courses);
			return addVideoPath;
		}
		videoService.add(video);
		return "redirect:" + videoAdress;		
	}
	@RequestMapping(value= "edit", method= RequestMethod.GET)
	public String edit(@RequestParam String id, Model model) {
		try {
			int index = Integer.parseInt(id);
			model.addAttribute("video", videoService.getById(index));
			model.addAttribute("courses", courseService.getAll());			
			return editVideoPath;
		}catch(Exception e) {
			return "redirect:" + adress404;
		}
		
	}
	@RequestMapping(value= "edit", method= RequestMethod.POST)
	public String edit(@Valid @ModelAttribute Video video,BindingResult errors, @RequestParam("fileUpload") MultipartFile file, Model model) {
		if(!file.isEmpty()) {
			try {
				imageService.delete(video.getImage());
				video.setImage(imageService.upload(file));		
			}catch(Exception e) {
				errors.rejectValue("image", "errors.course", e.toString());
			}
		}
		if(errors.hasErrors()) {
			List<Course> courses = courseService.getAll();
			model.addAttribute("courses",courses);
			return editVideoPath;
		}
		videoService.update(video);
		return "redirect:" + videoAdress;
	}
	@RequestMapping(value= "delete", method= RequestMethod.GET)
	public String delete(@RequestParam int id) {
		imageService.delete(videoService.getById(id).getImage());
		videoService.deleteById(id);
		return "redirect:" + videoAdress;
	}
}
