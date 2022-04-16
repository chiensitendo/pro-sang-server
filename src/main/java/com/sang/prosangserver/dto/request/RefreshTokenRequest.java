package com.sang.prosangserver.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RefreshTokenRequest {
	
	@NotBlank(message = "{account.username.not_blank}")
	private String username;
	
	@NotBlank(message = "{account.refresh-token.not_blank}")
	private String refreshToken;
}
