package com.sang.prosangserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sang.prosangserver.dto.response.GenericResponse;
import com.sang.prosangserver.dto.response.UserDetailResponse;
import com.sang.prosangserver.services.AccountService;
import com.sang.prosangserver.utils.ResponseUtils;

@RestController
@RequestMapping("account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/list")
	@ResponseBody
	public ResponseEntity<GenericResponse> getAccountList() {
		
		List<UserDetailResponse> list = accountService.getAccountList();
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(list));
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public ResponseEntity<GenericResponse> getAccountDetail(@PathVariable("id") Long id) {
		
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(accountService.getAccountDetail(id)));
	}
}
