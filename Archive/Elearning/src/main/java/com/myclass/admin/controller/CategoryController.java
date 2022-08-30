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

import com.myclass.entity.Category;
import com.myclass.service.CategoryService;

@Controller
@RequestMapping("${adress.category}")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@Value("${path.category}")
	private String categoryPath;
	@Value("${adress.category}")
	private String categoryAdress;

	@Value("${path.category-add}")
	private String addCategoryPath;

	@Value("${path.category-edit}")
	private String editCategoryPath;

	@Value("${adress.404}")
	private String adress404;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(Model model) {
		List<Category> categories = categoryService.getAll();
		model.addAttribute("categories", categories);
		return categoryPath;
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("category", new Category());
		return addCategoryPath;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute Category category, BindingResult error) {
		if (error.hasErrors()) {
			return addCategoryPath;
		}
		categoryService.add(category);
		return "redirect:" + categoryAdress;
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(Model model, @RequestParam String id) {
		try {
			int index = Integer.parseInt(id);
			model.addAttribute("category", categoryService.getById(index));
			return editCategoryPath;
		} catch (Exception e) {
			return "redirect:" + adress404;
		}
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(Model model, @Valid @ModelAttribute Category category, BindingResult error) {
		if (error.hasErrors()) {
			return editCategoryPath;
		}
		categoryService.update(category);
		return "redirect:" + categoryAdress;
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete(@RequestParam int id, Model model) {
		try {
			categoryService.deleteById(id);
			return "redirect:" + categoryAdress;
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("message", "Không thể xóa dữ liệu đã bị phụ thuộc");
			index(model);
			return categoryPath;
		}
	}
}
