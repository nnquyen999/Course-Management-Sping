package com.myclass.admin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class StatusController {
	@Value("${adress.role-add}")
	private String addRoleAdress;

	@Value("${adress.category-add}")
	private String addCategoryAdress;

	@Value("${adress.course-add}")
	private String addCourseAdress;

	@Value("${adress.home}")
	private String homeAdress;

	@Value("${path.error-406}")
	private String path406;

	@Value("${path.error-401}")
	private String path401;

	@Value("${path.error-403}")
	private String path403;

	@Value("${path.error-404}")
	private String path404;

	@RequestMapping(value = "${adress.406}", method = RequestMethod.GET)
	public String notAcceptable(@RequestParam String id, Model model){
		if(id.isEmpty()) {
			return path404;
		}else{
			switch (id) {
			case "role":
				model.addAttribute("message", "Vui lòng thêm dữ liệu ROLE trước.");
				model.addAttribute("link", addRoleAdress);
				break;
			case "category":
				model.addAttribute("message", "Vui lòng thêm dữ liệu ở Category trước.");
				model.addAttribute("link", addCategoryAdress);
				break;
			case "course":
				model.addAttribute("message", "Vui lòng thêm dữ liệu ở Course trước.");
				model.addAttribute("link", addCourseAdress);
				break;
			default:
				model.addAttribute("message", "Something Wrong.");
				model.addAttribute("link", homeAdress);
				break;
			}
			return path406;
		}
	}

	@RequestMapping(value = "${adress.401}", method = RequestMethod.GET)
	public String unauthorized() {
		return path401;
	}

	@RequestMapping(value = "${adress.403}", method = RequestMethod.GET)
	public String forbidden() {
		return path403;
	}
}
