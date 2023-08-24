package com.tyss.strongameapp.exception;

public class NameAlreadyExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NameAlreadyExistException() {
		super("Name Already Exist");
	}

	public NameAlreadyExistException(String message) {
		super(message);
	}
}
