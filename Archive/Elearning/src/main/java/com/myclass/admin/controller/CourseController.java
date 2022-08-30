package com.myclass.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myclass.entity.Category;
import com.myclass.entity.Course;
import com.myclass.service.CategoryService;
import com.myclass.service.CourseService;
import com.myclass.service.ImageService;

@Controller
@RequestMapping("${adress.course}")
public class CourseController {
	@Autowired
	private CourseService courseService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ImageService imageService;

	@Value("${path.course}")
	private String coursePath;
	@Value("${adress.course}")
	private String courseAdress;

	@Value("${path.course-add}")
	private String addCoursePath;

	@Value("${path.course-edit}")
	private String editCoursePath;

	@Value("${adress.406}")
	private String page406;

	@Value("${adress.404}")
	private String adress404;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(Model model) {
		List<Course> courses = courseService.getAll();
		model.addAttribute("courses", courses);
		return coursePath;
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Model model) {
		List<Category> categories = categoryService.getAll();
		if (categories.isEmpty()) {
			return "redirect:" + page406 + "?id=category";
		}
		Course course = new Course();
		model.addAttribute("categories", categories);
		model.addAttribute("course", course);
		return addCoursePath;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute Course course, BindingResult errors,
			@RequestParam("fileUpload") MultipartFile file, Model model) {
		if (!file.isEmpty()) {
			try {
				course.setImage(imageService.upload(file));
			} catch (Exception e) {
				errors.rejectValue("image", "errors.course", e.toString());
			}
		}

		if (errors.hasErrors()) {
			List<Category> categories = categoryService.getAll();
			model.addAttribute("categories", categories);
			return addCoursePath;
		}
		courseService.add(course);
		return "redirect:" + courseAdress;
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete(@RequestParam("id") Integer id, Model model) {
		try {
			imageService.delete(courseService.getById(id).getImage());
			courseService.deleteById(id);
			return "redirect:" + courseAdress;
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("message", "Không thể xóa dữ liệu đã bị phụ thuộc");
			index(model);
			return coursePath;
		} catch (Exception e) {
			model.addAttribute("message", "Lỗi, Không thể xóa");
			index(model);
			return coursePath;
		}

	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam("id") String id, Model model) {
		try {
			int index = Integer.parseInt(id);
			model.addAttribute("course", courseService.getById(index));
			model.addAttribute("categories", categoryService.getAll());
			return editCoursePath;
		} catch (Exception e) {
			return "redirect:" + adress404;
		}
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@Valid @ModelAttribute Course course, BindingResult errors,
			@RequestParam("fileUpload") MultipartFile file, Model model) {
		if (!file.isEmpty()) {
			try {
				imageService.delete(course.getImage());
				course.setImage(imageService.upload(file));
			} catch (Exception e) {
				errors.rejectValue("image", "errors.course", e.toString());
			}
		}
		if (errors.hasErrors()) {
			List<Category> categories = categoryService.getAll();
			model.addAttribute("categories", categories);
			return editCoursePath;
		}
		courseService.update(course);
		return "redirect:" + courseAdress;
	}
	// ----------------------------PRIVATE
	// METHOD------------------------------------

}
