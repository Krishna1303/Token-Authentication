package com.android.TokenAuthentication.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.android.TokenAuthentication.model.AppUserModel;
import com.android.TokenAuthentication.model.Mail;
import com.android.TokenAuthentication.model.TransactionModel;
import com.android.TokenAuthentication.model.UserModel;
import com.android.TokenAuthentication.service.AppUserRepository;
import com.android.TokenAuthentication.service.TransactionRepository;
import com.android.TokenAuthentication.service.UserRepository;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

@RestController
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AppUserRepository appuserRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
    private JavaMailSender emailSender;

	@RequestMapping("/getUserDetails")
	public UserModel getOwnerDetails(@RequestParam("accid") int id) {
		
	UserModel um=userRepository.findOne(id);
	if(um==null) {
		return um;
	}else {
		return um;
	}
	
	
	}
	
	@RequestMapping(value="/regUser",method=RequestMethod.POST)
	public AppUserModel regUser(@RequestParam("accid") int id,
			@RequestParam("username") String username,
			@RequestParam("password") String password) {
	
	UserModel userModel=userRepository.findOne(id);
	AppUserModel awu=new AppUserModel();
awu.setUsername(username);awu.setPassword(password);awu.setUserModel(userModel);awu.setAmount(0);
	AppUserModel appOwnerUser=appuserRepository.save(awu);
		return appOwnerUser;
	}
	
	@RequestMapping(value="/userLogin",method=RequestMethod.POST)
	public AppUserModel UserLogin(
			@RequestParam("username") String username,
			@RequestParam("password") String password) {
	
	
	AppUserModel awu=appuserRepository.findByusernameAndPassword(username,password);
	return awu;

	}
	
	@RequestMapping("/addMoney")
	public String addMoney(@RequestParam("userid") int userid,
			@RequestParam("amount")  double amount
			) {
		
		AppUserModel appUserModel=appuserRepository.findOne(userid);
		appUserModel.setAmount(amount);
		
		appUserModel=appuserRepository.save(appUserModel);
		
		if(appUserModel==null) {
			return "failed";
		}else {
			return "success";
		}}
		
		@RequestMapping("/sendMoney")
		public String sendMoney(@RequestParam("userid") int userid,
				@RequestParam("receiverid") int receiveid,
				@RequestParam("amount")  double amount
				) {
			double finalamount=0.0;
			AppUserModel appUserModel=appuserRepository.findOne(userid);
			double sendAmount=appUserModel.getAmount();
			if(sendAmount<amount) {
				return "no sufficient amount";
			}else {
				finalamount=sendAmount-amount;
		appUserModel.setAmount(finalamount);
		AppUserModel aum=	appuserRepository.save(appUserModel);
		AppUserModel  reciverappusermodel=appuserRepository.findOne(receiveid);
		double receiversAmount=reciverappusermodel.getAmount();
		reciverappusermodel.setAmount(amount+receiversAmount);
		appuserRepository.save(reciverappusermodel);
		TransactionModel tm=new TransactionModel();
		tm.setSenderid(userid);tm.setReciverid(receiveid);tm.setAmount(amount);
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("MM-dd-yyyy");
		tm.setDate(sdf.format(d));
		transactionRepository.save(tm);
		
		return "success";
			}
		
		
	}
		
		@RequestMapping(value="/userTransactions",method=RequestMethod.GET)
		public List<TransactionModel> UserTransactions(
				@RequestParam("userid") int userid)
		 {
		
			AppUserModel apum=appuserRepository.findOne(userid);
		
			
			
				return transactionRepository.findBysenderid(apum.getId());
		
		
		

		}
		
		@RequestMapping("/viewAppUser")
		public List<AppUserModel> viewAppUser(Model model) {
			
			List<AppUserModel> um= appuserRepository.findAll();
		
			return  um;
			
			}
		
		
		
		public void sendSimpleMessage(String path,String email) throws MessagingException {
Mail m=new Mail();
m.setTo(email.trim());m.setFrom("pvsaikrishna132@gmail.com");
m.setSubject("test"); m.setContent("test");
	        MimeMessage message = emailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message,
	                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
	                StandardCharsets.UTF_8.name());
	        FileSystemResource file = new FileSystemResource(path);
	        helper.addAttachment(file.getFilename(),file);
	        String inlineImage = "<img src=\"cid:logo.png\"></img><br/>";
	        

	        helper.setText(inlineImage + m.getContent(), true);
	        helper.setSubject(m.getSubject());
	        helper.setTo(m.getTo());
	        helper.setFrom(m.getFrom());

	        emailSender.send(message);
	    }
		
		@RequestMapping("/qrcode")
		public String generateQr(@RequestParam("userid") int userid,
				@RequestParam("receiverid") int receiveid,
				@RequestParam("amount")  double amount) throws MessagingException {
			
			AppUserModel appUserModel=appuserRepository.findOne(userid);
			UserModel userModel=appUserModel.getUserModel();
		String token=getSaltString();
			 ByteArrayOutputStream bout =
		                QRCode.from(String.valueOf(userid)+","+String.valueOf(receiveid)+","+String.valueOf(amount)+","+token)
		                        .withSize(250, 250)
		                        .to(ImageType.PNG)
		                        .stream();

		        try {
		   //    	OutputStream out=new FileOutputStream("/usr/local/apache/htdocs/guru/travel/qr-code.png");
		           OutputStream out = new FileOutputStream("c:/temp/qr-code.png");
		            bout.writeTo(out);
		            out.flush();
		            out.close();
		            sendSimpleMessage("c:/temp/qr-code.png",userModel.getEmail());
		            return "success";

		        } catch (FileNotFoundException e){
		            e.printStackTrace();
		            return "fail";
		        } catch (IOException e) {
		            e.printStackTrace();
		            return "fail";
		        }
			
		}
		
		
		
		public String getSaltString() {
	        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 16) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        String saltStr = salt.toString();
	        return saltStr;

	    }
		
		@RequestMapping("/profile")
		public AppUserModel getProfile(@RequestParam("userid") int userid) {
			
			return appuserRepository.findOne(userid);
			
		}
		
		@RequestMapping("/oldpassword")
		public String oldpassword(@RequestParam("username") String username,@RequestParam("oldpassword") String oldpassword){
			
			
	AppUserModel appOwnerUser=appuserRepository.findByusernameAndPassword(username, oldpassword);
		if(appOwnerUser==null) {
			return "fail";
		}else {
			return "success";
		}
		}

		@RequestMapping("/changepassword")
		public String changepassword(@RequestParam("userid") int userid,@RequestParam("password") String password){
			
			
		AppUserModel appOwnerUser=appuserRepository.findOne(userid);
		appOwnerUser.setPassword(password);
		AppUserModel aou=appuserRepository.save(appOwnerUser);
		if(aou==null) {
			return "fail";
		}else {
			return "success";
		}
		}
		
		@RequestMapping("/del")
		public void delete() {
			appuserRepository.deleteAll();
			transactionRepository.deleteAll();
			userRepository.deleteAll();
		}
	
	

}
