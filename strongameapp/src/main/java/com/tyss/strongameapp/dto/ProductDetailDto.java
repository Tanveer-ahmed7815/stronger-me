package com.tyss.strongameapp.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.tyss.strongameapp.entity.ProductDetailImage;

import lombok.Data;

@Data
public class ProductDetailDto {

	private int productId;

	private String productName;

	private String productDetails;

	private String productDeliveryDetails;

	private String productImage;

	private double productPrice;

	private double discount;

	private double actualCoins;

	private double finalPrice;

	private List<ProductDetailImage> productImageList;

	@NotNull
	private List<ProductSizeStockDto> productSizeStocks;

}
