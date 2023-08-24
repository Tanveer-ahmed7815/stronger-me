package com.tyss.strongameapp.exception;

public class AdminNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8557237897490359505L;

	public AdminNotFoundException() {
	}

	public AdminNotFoundException(String message) {
		super(message);
	}

}
