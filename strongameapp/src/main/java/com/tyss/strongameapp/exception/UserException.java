package com.tyss.strongameapp.exception;

/*
 * @author gunapal.p
 */
public class UserException extends RuntimeException {

	private static final long serialVersionUID = 903550257197339701L;

	public UserException() {
	}

	public UserException(String message) {
		super(message);
	}

}
