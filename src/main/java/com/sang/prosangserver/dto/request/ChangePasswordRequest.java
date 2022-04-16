package com.sang.prosangserver.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ChangePasswordRequest {
	
	@Size(max=100, message = "{validation.max.100}")
	@NotBlank(message = "{account.username.not_blank}")
	private String username;
	
	@Size(max=100, message = "{validation.max.100}")
	@NotBlank(message = "{account.password.not_blank}")
	private String password;
	
	@Size(max=100, message = "{validation.max.100}")
	@NotBlank(message = "{account.new-password.not_blank}")
	private String newPassword;
}
