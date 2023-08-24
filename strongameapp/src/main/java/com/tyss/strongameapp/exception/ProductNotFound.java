package com.tyss.strongameapp.exception;

import com.tyss.strongameapp.constants.CartConstants;

public class ProductNotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductNotFound() {
		super(CartConstants.PRODUCT_NOT_FOUND);
	}

	public ProductNotFound(String message) {
		super(message);
	}
}
