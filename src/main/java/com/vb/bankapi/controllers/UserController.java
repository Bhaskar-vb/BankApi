package com.vb.bankapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.vb.bankapi.entities.UserDetails;
import com.vb.bankapi.repos.UserRepository;

@Controller
@RestController
public class UserController {
	
	@Autowired
	UserRepository repository;
	UserDetails opuser;
	
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView  mav = new ModelAndView("index");
		UserDetails user = new UserDetails();
		user.setUsername("");
		user.setPassword("");
		mav.addObject("user",user);
		return mav;
	}
	
	@RequestMapping("/home")
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("home");
		return mav;
	}
	

	
	@RequestMapping("/authUser")
	public ModelAndView authUser( @ModelAttribute UserDetails user ) {
		
		ModelAndView  mav;
		UserDetails tempuser = repository.findByusername(user.getUsername());
		
		if(tempuser != null && tempuser.getPassword().equals(user.getPassword())) {
			mav = new ModelAndView("home");
			opuser = tempuser;
		}	
		else 
			mav = new ModelAndView("invalidlogin");
			
		// test with console
		System.out.println(" user Username : " + user.getUsername());
		System.out.println("user Password : " + user.getPassword());
		System.out.println(" user balance : " + user.getBalance());
		
		System.out.println(" tempuser Username : " + tempuser.getUsername());
		System.out.println("tempuser Password : " + tempuser.getPassword());
		System.out.println(" tempuser balance : " + tempuser.getBalance());
		
		
		System.out.println(" opuser Username : " + opuser.getUsername());
		System.out.println("opuser Password : " + opuser.getPassword());
		System.out.println(" opuser balance : " + opuser.getBalance());
		
		return mav;
	}
	
	@GetMapping("/viewbalance")
	public ModelAndView displaybalance() {
		ModelAndView  mav = new ModelAndView("viewbalance");
		mav.addObject("user",opuser);
		return mav;
	}
	
	@GetMapping("/withdraw")
	public ModelAndView withdraw() {
		ModelAndView  mav = new ModelAndView("withdraw");
		UserDetails user = new UserDetails();
		mav.addObject("user",user);
		System.out.println("amount from with draw " + user.getAmount());
		return mav;
	}
	
	@PostMapping("/withdraw")
	public ModelAndView postwithdraw(@ModelAttribute UserDetails user) {
		
		int rem = opuser.getBalance() - user.getAmount();
		if(rem >= 0) {
			opuser.setBalance(rem);
			repository.save(opuser);
			return  displaybalance();
		}
		else 
			return withdraw();
	
	}
	
	@GetMapping("/deposit")
	public ModelAndView deposit() {
		ModelAndView  mav = new ModelAndView("deposit");
		UserDetails user = new UserDetails();
		mav.addObject("user",user);
		System.out.println("amount from deposit " + user.getAmount());
		return mav;
	}
	
	@PostMapping("/deposit")
	public ModelAndView postdeposit(@ModelAttribute UserDetails user) {
		
		if(user.getAmount() >= 0) {
			opuser.setBalance(opuser.getBalance() + user.getAmount());
			repository.save(opuser);
			return  displaybalance();
		}
		else 
			return deposit();
	
	}
	
	@GetMapping("/details")
	public ModelAndView details() {
		ModelAndView  mav = new ModelAndView("details");
		mav.addObject("user",opuser);
		return mav;
	}
	
	@GetMapping("/newuser")
	public ModelAndView createUser() {
		ModelAndView mav = new ModelAndView("newuser");
		UserDetails user = new UserDetails();
		mav.addObject("user", user);
		return mav;
	}
	
	@PostMapping("/newuser")
	public ModelAndView postcreateUser(@ModelAttribute UserDetails user) {
		//ModelAndView mav = new ModelAndView("newuser");
		repository.save(user);
		opuser = user;
		System.out.println(" newuser Username : " + user.getUsername());
		System.out.println("newuser Password : " + user.getPassword());
		System.out.println(" newuser balance : " + user.getBalance());
		return home();
	}
	
	
	
	
	/*@RequestMapping(value = "/users",method = RequestMethod.GET)
	public List<UserDetails> getUsers(){
		return repository.findAll();
	}
	
	@RequestMapping(value = "/users/{id}",method = RequestMethod.GET)
	public UserDetails getUser(@PathVariable int id){
		return repository.findById(id).get();
	}
	
	@RequestMapping(value = "/users",method = RequestMethod.POST)
	public UserDetails createUser(@RequestBody UserDetails member ){
		return repository.save(member);
	}
	
	@RequestMapping(value = "/users",method = RequestMethod.PUT)
	public UserDetails updateUser(@RequestBody UserDetails member ){
		return repository.save(member);
	}
	
	@RequestMapping(value = "/users/{id}",method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable int id){
		 repository.deleteById(id);
	}*/
	
	
}
