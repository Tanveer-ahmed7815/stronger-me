package com.tyss.strongameapp.exception;

public class ProductException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductException() {
		super("Product Not Found");
	}

	public ProductException(String message) {
		super(message);
	}

}
