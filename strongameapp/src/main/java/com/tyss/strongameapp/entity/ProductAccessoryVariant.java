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

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Data
@Entity
@Table(name = "product_accessory_variant")
public class ProductAccessoryVariant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accessory_variant_id")
	private int accessoryVariantId;

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

	@Column(name = "color", length = 100)
	private String color;

	@Column(name = "unit", length = 45)
	private String unit;

	@Column(name = "size")
	private double size;

	@Column(name = "images", length = 1999)
	@Convert(converter = ListToStringConverter.class)
	private List<String> images;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Exclude
	@JsonManagedReference(value = "productaccessory-cart")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "productAccessory")
	private List<CartProduct> cartProducts;

	@Exclude
	@JsonBackReference(value = "product-accessory")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private ProductInformation accessoryProduct;

}
