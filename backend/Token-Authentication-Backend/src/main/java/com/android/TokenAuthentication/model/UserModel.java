package com.android.TokenAuthentication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@TableGenerator(name="tab", initialValue=10001, allocationSize=50)
public class UserModel {
	
	@Id
	 @GeneratedValue(strategy=GenerationType.TABLE, generator="tab")
	int id;
	String name;
	String mobile;
	String address;
	String email;
	String adhar;
	
	@OneToOne(mappedBy="userModel")
	@JsonBackReference
	public AppUserModel appUserModel;
	
	
	
	public AppUserModel getAppUserModel() {
		return appUserModel;
	}
	public void setAppUserModel(AppUserModel appUserModel) {
		this.appUserModel = appUserModel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAdhar() {
		return adhar;
	}
	public void setAdhar(String adhar) {
		this.adhar = adhar;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
