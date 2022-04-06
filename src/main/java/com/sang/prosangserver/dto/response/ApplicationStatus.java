package com.sang.prosangserver.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApplicationStatus {
	private String[] profiles;
	private String env;
	private LocalDateTime time = LocalDateTime.now();
	
	public ApplicationStatus() {}
	
	public ApplicationStatus(String[] profiles, String env) {
		this.profiles = profiles;
		this.env = env;
	}
}
