package com.tyss.strongameapp.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductInformationDto {

	private int cartProductId;

	private int variantId;

	private int productId;

	private String productName;

	private String productDetails;

	private String productDeliveryDetails;

	private String productImage;

	private String variantType;

	/**
	 * This is actual price of product WRT one quantity
	 */
	private double price;

	/**
	 * discount in terms of percentage WRT one quantity
	 */
	private double discount;

	/**
	 * coins restricted by admin
	 */
	private double coins;

	private int quantity;

	private String size;

	/**
	 * coins used for a product this can be select/change only at the time added
	 * product to cart
	 */
	private double usedCoins;

	/**
	 * after removing discount price and used coins from productPrice with respect
	 * to quantities
	 */
	private double toBePaid;

	/**
	 * after discount price WRT one quantity
	 */
	private double finalPrice;

	@NotNull
	private List<ProductSizeStockDto> productSizeStocks;

	private boolean isDeleted;

	private String message;

}
