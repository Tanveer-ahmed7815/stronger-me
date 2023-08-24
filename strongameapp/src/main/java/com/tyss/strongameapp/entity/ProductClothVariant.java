package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tyss.strongameapp.util.ListToStringConverter;

import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_cloth_variant")
public class ProductClothVariant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cloth_variant_id")
	private int clothVariantId;

	@NotNull
	@Column(name = "price")
	private double price;

	@NotNull
	@Column(name = "coins")
	private double coins;

	@NotNull
	@Column(name = "discount")
	private double discount;

	@NotNull
	@Column(name = "quantity")
	private int quantity;

	@NotNull
	@Column(name = "color", length = 100)
	private String color;

	@NotNull
	@Column(name = "size", length = 2)
	private String size;

	@Column(name = "images", length = 1999)
	@Convert(converter = ListToStringConverter.class)
	private List<String> images;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Exclude
	@JsonManagedReference(value = "productcloth-cart")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "productCloth")
	private List<CartProduct> cartProducts;

	@Exclude
	@JsonBackReference(value = "product-cloth")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private ProductInformation clothProduct;

}
