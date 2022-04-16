package com.sang.prosangserver.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	
	@Size(max=100, message = "{validation.max.100}")
	@NotBlank(message = "{account.username.not_blank}")
	private String username;
	
	@Size(max=100, message = "{validation.max.100}")
	@NotBlank(message = "{account.password.not_blank}")
	private String password;
}
