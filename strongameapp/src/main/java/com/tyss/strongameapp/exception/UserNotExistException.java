package com.tyss.strongameapp.exception;

import com.tyss.strongameapp.constants.UserConstants;

public class UserNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotExistException() {
		super(UserConstants.USER_NOT_FOUND);
	}

	public UserNotExistException(String message) {
		super(message);
	}

}
