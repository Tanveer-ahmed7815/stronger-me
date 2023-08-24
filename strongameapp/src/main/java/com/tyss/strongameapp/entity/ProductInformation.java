package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "product_information")
public class ProductInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int productId;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "product_details", length = 999)
	private String productDetails;

	@Column(name = "product_delivery_details")
	private String productDeliveryDetails;

	@Column(name = "product_image")
	private String productImage;

	@Column(name = "product_type")
	private String productType;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Fetch(value = FetchMode.SUBSELECT)
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	private List<ProductDetailImage> productImageList;

	@Exclude
	@JsonBackReference
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "product")
	private List<UserInformation> user;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Exclude
	@JsonManagedReference(value = "product-accessory")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "accessoryProduct")
	private List<ProductAccessoryVariant> accessoryVariants;

	@Exclude
	@JsonManagedReference(value = "product-cloth")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "clothProduct")
	private List<ProductClothVariant> clothVariants;

	@Exclude
	@JsonManagedReference(value = "product-supplement")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "supplementProduct")
	private List<ProductSupplementVariant> productSupplementVariant;

	@Exclude
	@JsonManagedReference(value = "product-order")
	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST })
	@JoinTable(name = "order_product", joinColumns = { @JoinColumn(name = "product_id") }, inverseJoinColumns = {
			@JoinColumn(name = "order_id") })
	private List<OrderInformation> order;

	@Exclude
	@JsonManagedReference(value = "product-shopbanner")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "shopbannerProduct")
	private List<ShoppingBannerInformation> productShopBanner;

}
