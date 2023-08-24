package com.tyss.strongameapp.dto;

import lombok.Data;

@Data
public class MyShopDTO {

	private int productId;

	private String productName;

	private String productImage;

	private double price;

	private double finalPrice;

	private double coins;

	private double discount;

}
