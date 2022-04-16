package com.sang.prosangserver.services;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sang.prosangserver.components.TokenComponent;
import com.sang.prosangserver.models.JWTToken;
import com.sang.prosangserver.utils.JwtTokenUtils;

@Service
public class JwtService {
	
	private final JwtTokenUtils jwtTokenUtils;
	
	private final TokenComponent tokenComponent;

	public JwtService(JwtTokenUtils jwtTokenUtils, TokenComponent tokenComponent) {
		this.jwtTokenUtils = jwtTokenUtils;
		this.tokenComponent = tokenComponent;
	}
	
	public JWTToken generateAccessToken(String username) {
		LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(tokenComponent.getSecretExpiredTime());
		String token = jwtTokenUtils.generateToken(tokenComponent.getSecret(), expiredTime, username);
		return new JWTToken(token, expiredTime);
	}
	
	public JWTToken generateRefreshToken(String username) {
		LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(tokenComponent.getRefreshSecretExpiredTime());
		String token = jwtTokenUtils.generateToken(tokenComponent.getRefreshSecret(), expiredTime, username);
		return new JWTToken(token, expiredTime);
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		return jwtTokenUtils.validateToken(token, userDetails, tokenComponent.getSecret());
	}
	
	public Boolean validateRefreshToken(String refreshToken, UserDetails userDetails) {
		return jwtTokenUtils.validateToken(refreshToken, userDetails, tokenComponent.getRefreshSecret());
	}
	
	public String getUsernameFromToken(String token) {
		return jwtTokenUtils.getUsernameFromToken(token, tokenComponent.getSecret());
	}
	
	public String getUsernameFromRefreshToken(String token) {
		return jwtTokenUtils.getUsernameFromToken(token, tokenComponent.getRefreshSecret());
	}
	
	public Boolean isRefreshTokenExpired(String token) {
		return jwtTokenUtils.isTokenExpired(token, tokenComponent.getRefreshSecret());
	}
}
