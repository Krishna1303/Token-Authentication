package com.android.TokenAuthentication.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.android.TokenAuthentication.model.TransactionModel;

public interface TransactionRepository extends JpaRepository<TransactionModel, Integer> {

	List<TransactionModel> findBysenderid(int id);

	

}
