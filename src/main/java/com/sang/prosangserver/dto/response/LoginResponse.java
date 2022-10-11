package com.sang.prosangserver.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	private Long id;
	private String username;
	private String email;
	private String name;
	private String photoUrl;
	private String accessToken;
	private LocalDateTime accessTokenExpiredTime;
	private String refreshToken;
	private LocalDateTime refreshTokenExpiredTime;
}
