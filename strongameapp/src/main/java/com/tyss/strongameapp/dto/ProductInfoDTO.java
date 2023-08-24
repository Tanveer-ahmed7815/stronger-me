package com.tyss.strongameapp.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductInfoDTO {

	private int productId;

	private String productName;

	private String productDetails;

	private String productDeliveryDetails;

	private String productImage;

	private List<String> firstVariant;

	private List<VariantDTO> secondVariant;

}
