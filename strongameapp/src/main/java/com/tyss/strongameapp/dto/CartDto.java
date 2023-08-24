package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class CartDto {

	private int userId;

	private List<ProductInformationDto> cartProduct;

	/**
	 * actual all products price excluding discount and used coins here quantities
	 * are considered
	 */
	private double actualCartPrice;

	/**
	 * by adding all discount prices (after converting it from percentage) and also
	 * quantities are considered
	 */
	private double totalDiscountPrice;

	/**
	 * total number coins used for all products (quantities are not considered
	 * since: (*) coins selection can only be done while adding product into cart
	 * (*) while adding quantity will be one
	 * 
	 */
	private double totalUsedCoins;

	/**
	 * final cart products price after removing discount price and used coins
	 * considering quantities
	 */
	private double finalCartPrice;

}
