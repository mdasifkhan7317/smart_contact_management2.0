package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;
import com.scm.services.impl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index() {
		
		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	public String home(Model model) {
		model.addAttribute("name","Asif khan");
		System.out.println("Home controller");
		return "home";
	}
	
	@RequestMapping("/about")
	public String about() {
		
		return "about";
	}
	@RequestMapping("/services")
	public String servicesPage() {
		
		return "services";
	}
	
	@GetMapping("/contact")
	public String contact() {
		
		return "contact";
	}
	
	// this is showing login page
	
	@GetMapping("/login")
	public String login() {
		
		return "login";
	}
	
	// registration page
	
	@GetMapping("/register")
	public String register(Model model) {
		UserForm userForm = new UserForm();
		
		model.addAttribute("userForm", userForm);
		
		return "register";
	}
	// processing register
	
	@PostMapping("/do-register")
	public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult, HttpSession session) {
		System.out.println(userForm);
		
		// fetch data
		//validate data
		
		if(rBindingResult.hasErrors()) {
			return "register";
		}
		
		//save to database
		//userservice
		
		//UserForm --> User
//		User user = User.builder()
//				.name(userForm.getName())
//				.email(userForm.getEmail())
//				.password(userForm.getPassword())
//				.about(userForm.getAbout())
//				.phoneNumber(userForm.getPhoneNumber())
//				.profilePic("https://static-00.iconduck.com/assets.00/profile-default-icon-2048x2045-u3j7s5nj.png")
//				.build();
//		
		
		User user = new User();
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setPassword(userForm.getPassword());
		user.setAbout(userForm.getAbout());
		user.setPhoneNumber(userForm.getPhoneNumber());
		user.setProfilePic("https://static-00.iconduck.com/assets.00/profile-default-icon-2048x2045-u3j7s5nj.png");
		
		User savedUser = userService.saveUser(user);
		
		//message
		// add the message
		Message message = Message.builder().content("Registration Sccessful").type(MessageType.green).build();
		session.setAttribute("message", message);
		
		//redirect to login page
		return "redirect:/register";
	}
	
}
