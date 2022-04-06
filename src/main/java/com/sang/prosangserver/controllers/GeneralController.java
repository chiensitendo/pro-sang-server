package com.sang.prosangserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sang.prosangserver.constants.GeneralConstants;
import com.sang.prosangserver.dto.response.ApplicationStatus;

@RestController
@RequestMapping
public class GeneralController {
	
	@Autowired
	Environment env;
	
	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("Hello!");
	}
	
	@GetMapping("/status")
	public ResponseEntity<ApplicationStatus> status() {
		return ResponseEntity.ok(new ApplicationStatus(env.getActiveProfiles(), System.getenv(GeneralConstants.ENV_PROFILE)));
	}
}
