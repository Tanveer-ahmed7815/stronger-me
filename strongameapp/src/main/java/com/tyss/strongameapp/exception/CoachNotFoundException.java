package com.tyss.strongameapp.exception;

public class CoachNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CoachNotFoundException() {
	}

	public CoachNotFoundException(String message) {
		super(message);
	}
}
