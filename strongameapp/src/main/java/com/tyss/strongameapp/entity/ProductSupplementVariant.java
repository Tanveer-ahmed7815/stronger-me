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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tyss.strongameapp.constants.ListToStringConverter;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "product_supplement_variant")
public class ProductSupplementVariant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supplement_variant_id")
	private int productSupplementVariantId;

	@Convert(converter = ListToStringConverter.class)
	@Column(name = "images")
	private List<String> images;

	@Column(name = "price")
	private double price;

	@Column(name = "discount")
	private double discount;

	@Column(name = "coins")
	private double coins;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "flavour")
	private String flavour;

	@Column(name = "unit")
	private String unit;

	@Column(name = "size")
	private double size;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Exclude
	@JsonManagedReference(value = "productsupplement-cart")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "productSupplement")
	private List<CartProduct> cartProducts;

	@Exclude
	@JsonBackReference(value = "product-supplement")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private ProductInformation supplementProduct;
}
