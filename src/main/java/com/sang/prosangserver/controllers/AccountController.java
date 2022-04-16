package com.sang.prosangserver.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sang.prosangserver.dto.request.ChangePasswordRequest;
import com.sang.prosangserver.dto.request.CreateAccountRequest;
import com.sang.prosangserver.dto.request.LoginRequest;
import com.sang.prosangserver.dto.request.LogoutRequest;
import com.sang.prosangserver.dto.request.RefreshTokenRequest;
import com.sang.prosangserver.dto.request.UpdateAccountDetailRequest;
import com.sang.prosangserver.dto.response.GenericResponse;
import com.sang.prosangserver.dto.response.UserDetailResponse;
import com.sang.prosangserver.services.AccountService;
import com.sang.prosangserver.services.AuthService;
import com.sang.prosangserver.utils.ResponseUtils;

@RestController
@RequestMapping("account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AuthService authService;
	
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
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<GenericResponse> generateNewAccount(@Valid @RequestBody CreateAccountRequest request) {
		
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(accountService.createAccount(request)));
	}
	
	@PutMapping("/{id}")
	@ResponseBody
	public ResponseEntity<GenericResponse> updateAccount(@PathVariable("id") Long id, @Valid @RequestBody UpdateAccountDetailRequest request) {
	
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(accountService.updateAccountDetail(id, request)));
	}
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<GenericResponse> accountLogin(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(authService.login(request)));
	};
	
	@PostMapping("/logout")
	@ResponseBody
	public ResponseEntity<?> logout(@Valid @RequestBody LogoutRequest request) {
		
		authService.logout(request.getId());
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/refresh-token")
	@ResponseBody
	public ResponseEntity<GenericResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(authService.refreshToken(request)));
	}
	
	@PostMapping("/{id}/password-change")
	@ResponseBody
	public ResponseEntity<GenericResponse> changePassword(@PathVariable("id") Long id, @Valid @RequestBody ChangePasswordRequest request) {
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(authService.changePassword(id, request)));
	}
	
}
