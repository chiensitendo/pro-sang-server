package com.sang.prosangserver.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenResponse {
	
	private String accessToken;
	private LocalDateTime accessTokenExpiredTime;
	private String refreshToken;
	private LocalDateTime refreshTokenExpiredTime;
}
