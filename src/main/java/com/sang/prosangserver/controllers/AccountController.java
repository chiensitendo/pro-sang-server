package com.sang.prosangserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sang.prosangserver.entities.Account;
import com.sang.prosangserver.repositories.AccountRepository;

@RestController
@RequestMapping("account")
public class AccountController {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@GetMapping("/list")
	public ResponseEntity<List<Account>> getAccountList() {
		List<Account> list = accountRepository.findAll();
		
		return ResponseEntity.ok(list);
	}
}
