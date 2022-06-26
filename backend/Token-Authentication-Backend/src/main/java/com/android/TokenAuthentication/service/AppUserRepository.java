package com.android.TokenAuthentication.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.android.TokenAuthentication.model.AppUserModel;

public interface AppUserRepository extends JpaRepository<AppUserModel, Integer> {

	AppUserModel findByusernameAndPassword(String username, String password);

}
