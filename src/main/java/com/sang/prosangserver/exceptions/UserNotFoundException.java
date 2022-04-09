package com.sang.prosangserver.exceptions;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6968784291078723254L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
