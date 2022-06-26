package com.android.TokenAuthentication.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.android.TokenAuthentication.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

}
