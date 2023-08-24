package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "myorder_details")
public class MyOrderDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7657280742217174902L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "myorder_id")
	private int myOrderId;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private double price;

	@Column(name = "type")
	private String type;

	@Column(name = "image")
	private String image;

	@Column(name = "order_status")
	private String orderStatus;

	@Column(name = "delivery_date")
	private Date deliveryDate;

	@Column(name = "paid_product_price")
	private double paidProductPrice;

	@Exclude
	@JsonBackReference(value = "orderInformation-myorder")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id")
	private OrderInformation orderInformation;

	@Exclude
	@JsonBackReference(value = "user-myorder")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserInformation userMyOrder;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "cart_product_id")
	private CartProduct cartProduct;

	public MyOrderDetails() {
		super();
	}

	public MyOrderDetails(int myOrderId, String name, double price, String type, String image,
			double paidProductPrice) {
		super();
		this.myOrderId = myOrderId;
		this.name = name;
		this.price = price;
		this.type = type;
		this.image = image;
		this.paidProductPrice = paidProductPrice;
	}

}
