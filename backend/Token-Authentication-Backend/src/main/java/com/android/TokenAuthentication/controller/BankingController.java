package com.android.TokenAuthentication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.android.TokenAuthentication.model.AppUserModel;
import com.android.TokenAuthentication.model.TransactionModel;
import com.android.TokenAuthentication.model.UserModel;
import com.android.TokenAuthentication.service.AppUserRepository;
import com.android.TokenAuthentication.service.TransactionRepository;
import com.android.TokenAuthentication.service.UserRepository;

@Controller
public class BankingController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	AppUserRepository appuserRepository;
	
	@RequestMapping("/")
	public String adminlogin() {
		return "Login";
		}
	
	@RequestMapping("/adminLogin")
	public String admin(@RequestParam("username") String username,@RequestParam("password")String password) {
		if(username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
			return "adminlogin";
		}else {
			return "Login";
		}
		
		
		}
	
	
	@RequestMapping("/home")
	public String home() {
	
			return "adminlogin";
	
	}
	
	@RequestMapping("/addUseForm")
	public String addUserForm(Model model) {
		UserModel userModel=new UserModel();
		model.addAttribute("user", userModel);
		return "addUserForm";
		}
	
	@RequestMapping("/addUser")
	public String addUser(Model model,@ModelAttribute("user") UserModel user) {
		UserModel usermodel=userRepository.save(user);
		if(usermodel==null) {
			model.addAttribute("msg", "Failed to Add User");
			return "addUserForm";
		}else {
			UserModel userModel=new UserModel();
			model.addAttribute("user", userModel);
			model.addAttribute("msg", "Added Successfully");
			model.addAttribute("accno", "account number is:"+usermodel.getId());
			return "addUserForm";
		}
		
		
		}
	
	
	
	@RequestMapping("/viewUser")
	public String viewUser(Model model) {
		
		List<UserModel> um= userRepository.findAll();
		model.addAttribute("user", um);
		return "viewusers";
		
		}
	
	@RequestMapping("/viewTrans")
	public String viewTrans(Model model,@RequestParam("accid") int accid) {
		
	UserModel um=userRepository.findOne(accid);
	AppUserModel apum=um.getAppUserModel();
	
			List<TransactionModel> tm= transactionRepository.findBysenderid(apum.getId());
		model.addAttribute("trans", tm);
		return "viewtrans";
		
		}
	
	@RequestMapping(value="/addMoney",method=RequestMethod.POST)
	public String addMoney(Model model,@RequestParam("accid") int accid,@RequestParam("amount") double amount) {
		
	UserModel um=userRepository.findOne(accid);
	AppUserModel apum=um.getAppUserModel();
	apum.setAmount(amount);
	
 apum=appuserRepository.save(apum);
 if(apum==null) {
	 model.addAttribute("msg","failed");
	 return "addMoneyForm";
 }else {
	model.addAttribute("msg","Added successfully");
	return "addMoneyForm";
 }
	
	}
	
	@RequestMapping("/addMoneyForm")
	public String addMoneyForm(Model model) {
List<Integer> accnos=new ArrayList();		
List<UserModel> usermodel=userRepository.findAll();
for(UserModel um:usermodel) {
	System.out.println(um.getId());
	accnos.add(um.getId());}
	model.addAttribute("accnos",accnos);

	return "addMoneyForm";
 }
	
	}
	
	


