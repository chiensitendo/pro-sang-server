package com.sang.prosangserver.enums;

public enum ErrorMessages {
	USER_NOTFOUND("user.not_found"),
	USER_EXISTS("user.exists"),
	INTERNAL_SERVER_ERROR("internal_server_error"),
	UNAUTHORIZED("unauthorized");
	private ErrorMessages(String message) {
		this.message = message;
	}
	
	private String message;
	
	public String getMessage() {
		return this.message;
	}
}
