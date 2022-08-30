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

import com.myclass.dto.TargetDto;
import com.myclass.entity.Course;
import com.myclass.entity.Target;
import com.myclass.service.CourseService;
import com.myclass.service.TargetService;

@Controller
@RequestMapping("${adress.target}")
public class TargetController {
	@Autowired
	private TargetService targetService;
	
	@Autowired
	private CourseService courseService;
	
	@Value("${path.target}")
	private String targetPath;	
	@Value("${adress.target}")
	private String targetAdress;
	
	@Value("${path.target-add}")
	private String addTargetPath;
	
	@Value("${path.target-edit}")
	private String editTargetPath;
	
	@Value("${adress.406}")
	private String page406;
	
	@Value("${adress.404}")
	private String adress404;
	
	@RequestMapping(value= "", method= RequestMethod.GET)
	public String index(Model model) {
		List<TargetDto> targets = targetService.getAllDto();	
		model.addAttribute("targets",targets);
		return targetPath;
	}
	@RequestMapping(value= "add", method= RequestMethod.GET)
	public String add(Model model) {
		List<Course> course = courseService.getAll();
		if(course.isEmpty()) {
			return "redirect:"+ page406 + "?id=course\"";
		}
		model.addAttribute("courses", course);
		model.addAttribute("target", new Target());
		return addTargetPath;
	}
	@RequestMapping(value= "add", method= RequestMethod.POST)
	public String add(@Valid @ModelAttribute("target") Target target, BindingResult errors) {
		if(errors.hasErrors()) {
			return addTargetPath;
		}
		targetService.add(target);
		return "redirect:"+ targetAdress;
	}
	
	
	@RequestMapping(value="edit", method = RequestMethod.GET)
	public String edit(Model model, @RequestParam String id) {
		try {
			int index = Integer.parseInt(id);
			model.addAttribute("target", targetService.getById(index));
			model.addAttribute("courses", courseService.getAll());
			return editTargetPath;
		}catch(Exception e) {
			return "redirect:" + adress404;
		}
		
	}
	@RequestMapping(value="edit", method = RequestMethod.POST)
	public String edit(@Valid @ModelAttribute("target") Target target, BindingResult errors, Model model) {
		if(errors.hasErrors()) {
			model.addAttribute("courses", courseService.getAll());
			return editTargetPath;
		}
		targetService.update(target);	
		return "redirect:"+ targetAdress;
	}
	@RequestMapping(value="delete", method = RequestMethod.GET)
	public String delete(@RequestParam("id") Integer id, Model model) {
			targetService.deleteById(id);
			return "redirect:"+ targetAdress;		
	}
}
