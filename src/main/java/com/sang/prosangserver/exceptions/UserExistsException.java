package com.sang.prosangserver.exceptions;

public class UserExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7328330507678008543L;
	
	public UserExistsException(String message) {
		super(message);
	}

}
