package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductAccessoryVariantDTO {
	
	private int accessoryVariantId;

	private double price;

	private double coins;

	private double discount;
	
	private int quantity;

	private String color;

	private String unit;

	private double size;

	private List<String> images;

}
