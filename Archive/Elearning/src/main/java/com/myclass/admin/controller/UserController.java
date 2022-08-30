package com.myclass.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.myclass.dto.UserDto;
import com.myclass.entity.Role;
import com.myclass.entity.User;
import com.myclass.service.RoleService;
import com.myclass.service.ImageService;
import com.myclass.service.UserService;

@Controller
@RequestMapping("${adress.user}")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ImageService imageService;
	
	@Value("${path.user}")
	private String userPath;	
	@Value("${adress.user}")
	private String userAdress;
	
	@Value("${path.user-add}")
	private String addUserPath;
	
	@Value("${path.user-edit}")
	private String editUserPath;
	
	@Value("${path.user-password}")
	private String passwordUserPath;
	
	@Value("${adress.406}")
	private String page406;
	
	@Value("${adress.404}")
	private String adress404;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		List<UserDto> users = userService.getAllDto();
		modelMap.addAttribute("users", users);
		return userPath;
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Model model) {
		List<Role> roles = roleService.getAll();
		if(roles.isEmpty()) {
			return "redirect:"+ page406+ "?id=role";
		}
		
		model.addAttribute("roles", roles);
		model.addAttribute("user", new User());
		return addUserPath;
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("user") User user, BindingResult errors, @RequestParam String confirm,
			Model model, @RequestParam("fileUpload") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				user.setAvatar(imageService.upload(file));
			} catch (Exception e) {
				errors.rejectValue("avatar", "error.course", e.getMessage());
			}
		}

		if (errors.hasErrors() || !passwordMatch(user.getPassword(), confirm, errors) || duplicateEmail(user, errors)) {
			List<Role> roles = roleService.getAll();
			model.addAttribute("roles", roles);
			return addUserPath;
		}
		userService.add(user);
		return "redirect:" + userAdress;
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(ModelMap modelMap, @RequestParam("id") String id) {
		try {
			int index = Integer.parseInt(id);
			modelMap.addAttribute("user", userService.getById(index));
			modelMap.addAttribute("roles", roleService.getAll());			
			return editUserPath;
		}catch(Exception e) {
			return "redirect:" + adress404;
		}
		
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@Valid @ModelAttribute("user") User user, BindingResult errors, Model model, @RequestParam("fileUpload") MultipartFile file) {
		if(!file.isEmpty()) {
			try {
				imageService.delete(user.getAvatar());
				user.setAvatar( imageService.upload(file));			
			}catch(Exception e) {
				errors.rejectValue("image", "errors.course", e.toString());
			}
		}		
		
		if (duplicateEmail(user, errors) || errors.hasFieldErrors("fullname") || errors.hasFieldErrors("email")) {
			List<Role> roles = roleService.getAll();
			model.addAttribute("roles", roles);
			return editUserPath;
		}
		userService.updateInformation(user);
		return "redirect:" + userAdress;
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete(@ModelAttribute("id") int id) {
		imageService.delete(userService.getById(id).getAvatar());
		userService.deleteById(id);
		return "redirect:" + userAdress;
	}

	@RequestMapping(value = "password", method = RequestMethod.GET)
	public String password(@ModelAttribute("id") int id, Model model) {
		User user = userService.getById(id);
		model.addAttribute("user", user);
		return passwordUserPath;
	}

	@RequestMapping(value = "password", method = RequestMethod.POST)
	public String password(@Valid @ModelAttribute User user, BindingResult errors, @RequestParam String confirm) {
		System.out.println(user.toString()+ confirm);
		if (passwordMatch(user.getPassword(), confirm, errors) && !errors.hasFieldErrors("password")) {
			userService.updatePassword(user);
			return "redirect:" + userAdress;
		}
		return passwordUserPath;
	}

	/*------------------------------PRIVATE METHOD------------------------------------------*/
	private boolean passwordMatch(String password, String passConfirm, BindingResult errors) {
		if (password.equals(passConfirm)) {
			System.out.println("Password match");
			return true;
		}
		errors.rejectValue("password", "user", "Mật khẩu xác nhận không chính xác");
		System.out.println("Check pass false");
		return false;
	}

	private boolean duplicateEmail(User user, BindingResult errors) {
		User entity = userService.findByEmail(user.getEmail());
		if (entity == null || entity.getId() == user.getId()) {
			System.out.println("Check Email NOTHING");
			return false;
		} else {
			System.out.println("Email duplicate");
			errors.rejectValue("email", "user", "Email đã tồn tại");
			return true;
		}
	}
}
