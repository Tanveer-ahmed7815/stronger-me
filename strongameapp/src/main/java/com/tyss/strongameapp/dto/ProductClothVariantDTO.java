package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductClothVariantDTO {
	
	private int clothVariantId;
	
	private double price;

	private double coins;

	private double discount;

	private int quantity;

	private String color;

	private String size;

	private List<String> images;

}
