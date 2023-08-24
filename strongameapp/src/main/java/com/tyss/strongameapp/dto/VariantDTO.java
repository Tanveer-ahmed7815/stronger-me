package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class VariantDTO {

	private int id;

	private double price;
	
	private double finalPrice;

	private double coins;

	private double discount;

	private int quantity;
	
	private String flavour;
	
	private String color;

	private String sizeWithUnit;

	private String size;

	private List<String> images;

}
