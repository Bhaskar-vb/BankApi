package com.vb.bankapi.services;


import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.vb.bankapi.entities.UserDetails;
import com.vb.bankapi.repos.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository repository;
	UserDetails opuser;
	

	public ModelAndView index() {
		ModelAndView  mav = new ModelAndView("index");
		UserDetails user = new UserDetails();
		user.setUsername("");
		user.setPassword("");
		mav.addObject("user",user);
		return mav;
	}
	
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("home");
		return mav;
	}
	


	public ModelAndView authUser( UserDetails user) {
		ModelAndView  mav;
		UserDetails tempuser = repository.findByusername(user.getUsername());
		if(tempuser != null && tempuser.getPassword().equals(Integer.toString(user.getPassword().hashCode()))) {
			mav = new ModelAndView("home");
			opuser = tempuser;
		}	
		else 
			mav = new ModelAndView("invalidlogin");
		
		return mav;
	}
	
	public ModelAndView displaybalance() {
		ModelAndView  mav = new ModelAndView("viewbalance");
		mav.addObject("user",opuser);
		return mav;
	}
	
	
	
	public ModelAndView withdraw() {
		ModelAndView mav = new ModelAndView("withdraw");
		UserDetails user = new UserDetails();
		mav.addObject("user", user);
		return mav;
	}
	
	public ModelAndView postwithdraw(UserDetails user) {

		int rem = opuser.getBalance() - user.getAmount();
		if (user.getAmount() >=0 && rem >= 0) {
			opuser.setBalance(rem);
			repository.save(opuser);
			return displaybalance();
		} else
			return new ModelAndView("failedtran");

	}
	
	public ModelAndView deposit() {
		ModelAndView  mav = new ModelAndView("deposit");
		UserDetails user = new UserDetails();
		mav.addObject("user",user);
		return mav;
	}
	
	public ModelAndView postdeposit( UserDetails user) {

		if (user.getAmount() >= 0) {
			opuser.setBalance(opuser.getBalance() + user.getAmount());
			repository.save(opuser);
			return displaybalance();
		} else
			return new ModelAndView("failedtran");

	}
	
	public ModelAndView details() {
		ModelAndView mav = new ModelAndView("details");
		mav.addObject("user", opuser);
		return mav;
	}
	
	public ModelAndView createUser() {
		ModelAndView mav = new ModelAndView("newuser");
		UserDetails user = new UserDetails();
		mav.addObject("user", user);
		return mav;
	}
	
	public ModelAndView postcreateUser(@ModelAttribute UserDetails user) {
		user.setAccnum(genaccnum());
		user.setPassword(Integer.toString(user.getPassword().hashCode()));
		repository.save(user);
		opuser = user;
		return home();
	}
	
	public ModelAndView forgot() {
		ModelAndView mav = new ModelAndView("forgot");
		mav.addObject("user", new UserDetails());
		return mav;
	}
	
	public ModelAndView postforgot(UserDetails user) {
		ModelAndView mav;
		UserDetails tempusr = repository.findByIdAndPin(user.getId(), user.getPin());
		if(tempusr != null && tempusr.getId() == user.getId() && tempusr.getPin() ==  user.getPin()) {
			mav = new ModelAndView("setpass");
			opuser = tempusr;
			mav.addObject("user", opuser);
		}
		else {
			mav = new ModelAndView("invalidlogin");
		}
		
		return mav;
	}
	
	public ModelAndView setpass() {
		ModelAndView mav = new ModelAndView("setpass");
		UserDetails user = new UserDetails();
		user.setUsername(opuser.getUsername());
		mav.addObject("user", user);
		return mav;
	}
	
	public ModelAndView postsetpass(UserDetails user) {
		opuser.setPassword(Integer.toString(user.getPassword().hashCode()));
		repository.save(opuser);
		return home();
	}
	
	private String genaccnum() {
		Random rand = new Random();
		String card = "";
		for (int i = 0; i < 14; i++) {
			int n = rand.nextInt(10) + 0;
			card += Integer.toString(n);
		}
		return card;
	}

	public ModelAndView logout() {
		opuser = null;
		return index();
	}

}
