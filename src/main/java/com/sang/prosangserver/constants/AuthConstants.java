package com.sang.prosangserver.constants;

public class AuthConstants {

	public static final int PASSWORD_EXPIRED_MONTHS = 3; //months
	
	public static final String[] IGNORE_AUTH_PATHS = new String[] {
			"/account/login", "/account/refresh-token"
	};
}
