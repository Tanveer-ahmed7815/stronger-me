package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class ProductSizeStockDto {

	private int variantId;

	private String size;

	private int stockAvailable;

	public ProductSizeStockDto() {
		super();
	}

}
