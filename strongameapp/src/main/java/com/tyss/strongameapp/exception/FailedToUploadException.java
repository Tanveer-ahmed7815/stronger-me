package com.tyss.strongameapp.exception;

public class FailedToUploadException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FailedToUploadException() {
	}

	public FailedToUploadException(String message) {
		super(message);
	}
}
