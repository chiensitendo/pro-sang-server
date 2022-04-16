package com.sang.prosangserver.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LogoutRequest {

	@NotNull(message = "{account.id.not_null}")
	private Long id;
}
