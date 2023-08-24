package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductSupplementVariantDTO {
	
	private int productSupplementVariantId;
	
	private List<String> images;
	
	private double price;

	private double discount;

	private double coins;

	private int quantity;

	private String flavour;

	private String unit;
	
	private double size;

}
