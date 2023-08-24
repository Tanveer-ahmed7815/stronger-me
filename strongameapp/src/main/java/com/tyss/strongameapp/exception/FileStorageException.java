package com.tyss.strongameapp.exception;

import java.io.IOException;

public class FileStorageException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileStorageException(String message, IOException ex) {
		super(message);
	}

	public FileStorageException(String string) {
		super(string);
	}

}
