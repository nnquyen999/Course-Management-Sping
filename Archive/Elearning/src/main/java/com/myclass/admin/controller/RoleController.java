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

import com.myclass.entity.Role;
import com.myclass.service.RoleService;



@Controller
@RequestMapping("${adress.role}")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Value("${path.role}")
	private String rolePath;	
	@Value("${adress.role}")
	private String roleAdress;
	
	@Value("${path.role-add}")
	private String addRolePath;
	
	@Value("${path.role-edit}")
	private String editRolePath;
	
	@Value("${adress.404}")
	private String adress404;
	
	@RequestMapping(value="",method = RequestMethod.GET)
	public String index(Model model) {
		List<Role> roles = roleService.getAll();	
		model.addAttribute("roles",roles);
		return rolePath;
	}
	@RequestMapping(value="add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("role", new Role());
		return addRolePath;
	}
	
	@RequestMapping(value="add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("role") Role role, BindingResult errors) {		
		if(errors.hasErrors()) {
			return addRolePath;
		}
		roleService.add(role);
		return "redirect:" + roleAdress;
	}	
	
	@RequestMapping(value="edit", method = RequestMethod.GET)
	public String edit(Model model, @ModelAttribute("id") String id){
		try {
			int index = Integer.parseInt(id);
			model.addAttribute("role", roleService.getById(index));
			return editRolePath;
		}catch(Exception e) {
			return "redirect:" + adress404;
		}
	}
	

	@RequestMapping(value="edit", method = RequestMethod.POST)
	public String edit(@Valid @ModelAttribute("role") Role role, BindingResult errors) {
		if(errors.hasErrors()) {
			return editRolePath;
		}
		roleService.update(role);	
		return "redirect:" + roleAdress;
	}
	@RequestMapping(value="delete", method = RequestMethod.GET)
	public String delete(@RequestParam("id") Integer id, Model model) {
		try {
			roleService.deleteById(id);
			return "redirect:" + roleAdress;
		} catch (DataIntegrityViolationException e) {
			model.addAttribute("message","Không thể xóa dữ liệu đã bị phụ thuộc");
			index(model);
			return rolePath;
		}
		
	}
	
	// -------------------------------METHOD PRIVATE----------------------------------------------
	
}
