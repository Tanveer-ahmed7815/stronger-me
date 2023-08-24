package com.tyss.strongameapp.exception;

public class ModuleContentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModuleContentException() {
		super("No Module/Content/Specialization Found");
	}

	public ModuleContentException(String message) {
		super(message);
	}
}
