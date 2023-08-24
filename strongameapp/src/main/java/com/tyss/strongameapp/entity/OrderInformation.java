package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "order_information")
public class OrderInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9134661968550173133L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private int orderId;

	@Column(name = "order_date")
	private Date orderDate;

	@Exclude
	@JsonManagedReference(value = "orderInformation-myorder")
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "orderInformation")
	private List<MyOrderDetails> myorder;

	@Exclude
	@JsonBackReference(value = "product-order")
	@ManyToMany(mappedBy = "order")
	private List<ProductInformation> orderProducts;

	@Exclude
	@JsonBackReference(value = "user-order")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserInformation orderUser;

	@Exclude
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "saved_address_id")
	private SavedAddress savedAddress;

	public OrderInformation() {
		super();
	}
}
