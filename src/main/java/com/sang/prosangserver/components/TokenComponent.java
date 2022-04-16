package com.sang.prosangserver.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class TokenComponent {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.secret.expired}")
	private Long secretExpiredTime;
	
	@Value("${jwt.refresh-secret}")
	private String refreshSecret;
	
	@Value("${jwt.refresh-secret.expired}")
	private Long refreshSecretExpiredTime;
}
