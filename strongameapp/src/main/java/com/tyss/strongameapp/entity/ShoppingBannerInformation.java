package com.tyss.strongameapp.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.ToString.Exclude;

@Entity
@Data
@Table(name = "shopping_banner_information")
public class ShoppingBannerInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shopping_banner_id")
	private int shoppingBannerId;

	@Column(name = "shopping_banner_image")
	private String shoppingBannerImage;

	@Exclude
	@JsonBackReference(value = "product-shopbanner")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private ProductInformation shopbannerProduct;

	public ShoppingBannerInformation() {
		super();
	}

	public ShoppingBannerInformation(int shoppingBannerId,
			String shoppingBannerImage) {
		super();
		this.shoppingBannerId = shoppingBannerId;
		this.shoppingBannerImage = shoppingBannerImage;
	}

}