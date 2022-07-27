package com.vb.bankapi.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.vb.bankapi.entities.UserDetails;
import com.vb.bankapi.services.UserService;

@Controller
@RestController
public class UserController {
	
	
	@Autowired
	UserService services;
	
	@RequestMapping("/index")
	public ModelAndView index() {
		return services.index();
	}
	
	@RequestMapping("/home")
	public ModelAndView home() {
		return services.home();
	}
	
	@RequestMapping("/authUser")
	public ModelAndView authUser( @ModelAttribute UserDetails user ) {
		return services.authUser(user);
	}
	
	@GetMapping("/viewbalance")
	public ModelAndView displaybalance() {
		return services.displaybalance();
	}
	
	@GetMapping("/withdraw")
	public ModelAndView withdraw() {
		return services.withdraw();
	}
	
	@PostMapping("/withdraw")
	public ModelAndView postwithdraw(@ModelAttribute UserDetails user) {
		return services.postwithdraw(user);
	}
	
	@GetMapping("/deposit")
	public ModelAndView deposit() {
		return services.deposit();
	}
	
	@PostMapping("/deposit")
	public ModelAndView postdeposit(@ModelAttribute UserDetails user) {
		return services.postdeposit(user);
	}
	
	
	@GetMapping("/details")
	public ModelAndView details() {
		return services.details();
	}
	
	@GetMapping("/newuser")
	public ModelAndView createUser() {
		return services.createUser();
	}
	
	@PostMapping("/newuser")
	public ModelAndView postcreateUser(@ModelAttribute UserDetails user) {
		return services.postcreateUser(user);
	}
	
	@GetMapping("/forgot")
	public ModelAndView forgot() {
		return services.forgot();
	}
	
	@PostMapping("/forgot")
	public ModelAndView postforgot(@ModelAttribute UserDetails user) {
		return services.postforgot(user);
	}
	
	@GetMapping("/setpass")
	public ModelAndView setpass() {
		return services.setpass();
	}
	
	@PostMapping("/setpass")
	public ModelAndView postsetpass(@ModelAttribute UserDetails user) {
		return services.postsetpass(user);
	}
	
	
	@RequestMapping(value = "/logout")
	public ModelAndView logout() {
		return services.logout();
	}
		
	
}
