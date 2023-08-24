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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Data
@Entity
@Table(name = "payment_details")
//@NoArgsConstructor
//@AllArgsConstructor
public class PaymentDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -888088471784173065L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private int paymentId;

	@Column(name = "payment_detail")
	private String paymentDetail;

	@Column(name = "payment_mode")
	private String paymentMode;

	@Column(name = "payment_date")
	private Date paymentDate;

	@Column(name = "amount")
	private double amount;
	
	@Column(name = "type",length = 15)
	private String type;
	
	@Column(name = "type_id")
	private int typeId;
	
	@Column(name= "razorpay_payment_id")
    private String razorpayPaymentId;
	
	@Column(name= "razorpay_order_id")
	private String razorpayOrderId;
	
	@Column(name= "razorpay_signature")
	private String razorpaySignature;

	@Exclude
    @JsonBackReference(value="user-payment")
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private UserInformation paymentUser;
	

}
