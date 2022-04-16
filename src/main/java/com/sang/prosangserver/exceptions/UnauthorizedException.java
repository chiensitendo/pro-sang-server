package com.sang.prosangserver.exceptions;

public class UnauthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2740521476842516152L;
	
	
	public UnauthorizedException() {
	}
	
	public UnauthorizedException(String message) {
		super(message);
	}

}
