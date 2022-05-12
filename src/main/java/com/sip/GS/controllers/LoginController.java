package com.sip.GS.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sip.GS.entities.User;
import com.sip.GS.service.UserService;

@Controller
public class LoginController {
	
	public static String uploadDirectory = 
			System.getProperty("user.dir")+"/src/main/resources/static/uploads";
	
	@Autowired
	 private UserService userService;
	
	@RequestMapping(value={"/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		 ModelAndView modelAndView = new ModelAndView();
		 modelAndView.setViewName("login");
		 return modelAndView;
	}
	
	
	 
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		
		 if (userExists != null) {
			 bindingResult.rejectValue("email", "error.user","There is already a user registered with the email provided");
		 }
		 if (bindingResult.hasErrors()) {
			 modelAndView.setViewName("registration");
		 } 
		 else {
			 userService.saveUser(user);
			 modelAndView.addObject("successMessage", "User has been registered successfully");
			 modelAndView.addObject("user", new User());
			 modelAndView.setViewName("login");
		}
		return modelAndView;
	 }

	
	@GetMapping("/403")
	 public String error403() {
		return "/error/403";
	 }

}
