package com.tyss.strongameapp.entity;

import java.io.Serializable;

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
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "cart_product")
public class CartProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7534805958206826386L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_product_id")
	private int cartProductId;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "used_coins")
	private double usedCoins;

	@Column(name = "to_be_paid")
	private double toBePaid;

	@Column(name = "is_ordered")
	private boolean isOrderd;

	@Exclude
	@JsonBackReference(value = "productaccessory-cart")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "accessory_variant_id")
	private ProductAccessoryVariant productAccessory;

	@Exclude
	@JsonBackReference(value = "productcloth-cart")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "cloth_variant_id")
	private ProductClothVariant productCloth;

	@Exclude
	@JsonBackReference(value = "productsupplement-cart")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "supplement_variant_id")
	private ProductSupplementVariant productSupplement;

	@Exclude
	@JsonBackReference(value = "user-cartproduct")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserInformation cartProudutUser;

}
