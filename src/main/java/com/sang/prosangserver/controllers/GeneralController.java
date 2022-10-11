package com.sang.prosangserver.controllers;

import com.sang.prosangserver.dto.response.GenericResponse;
import com.sang.prosangserver.services.ShellUserService;
import com.sang.prosangserver.utils.ResponseUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sang.prosangserver.constants.GeneralConstants;
import com.sang.prosangserver.dto.response.ApplicationStatus;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
public class GeneralController {

	final ShellUserService shellUserService;

	public GeneralController(final ShellUserService shellUserService) {
		this.shellUserService = shellUserService;
	}
	
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

	@GetMapping("/shell-name")
	public ResponseEntity<GenericResponse> name(HttpServletRequest request) {
		String name = request.getParameter("name");
		String sha256hex = DigestUtils.sha256Hex(request.getRemoteAddr());
		return ResponseEntity.ok(ResponseUtils.buildOkResponse(shellUserService.getShellUserInfo(sha256hex, name)));
	}
}
