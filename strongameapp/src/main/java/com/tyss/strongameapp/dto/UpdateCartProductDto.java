package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class UpdateCartProductDto {

	private int cartProductId;

	private int variantId;

	private int productId;

	private int quantity;

}
