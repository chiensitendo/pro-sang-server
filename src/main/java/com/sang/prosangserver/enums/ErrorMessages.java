package com.sang.prosangserver.enums;

public enum ErrorMessages implements EnumMessageInterface {
	USER_NOTFOUND("user.not_found"),
	USER_EXISTS("user.exists"),
	INTERNAL_SERVER_ERROR("internal_server_error"),
	UNAUTHORIZED("unauthorized"),
	ACCOUNT_EXISTS("account.exists");
	private ErrorMessages(String message) {
		this.message = message;
	}

	private String message;
	@Override
	public String getMessage() {
		return this.message;
	}
}
