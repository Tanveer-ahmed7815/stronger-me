package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyss.strongameapp.util.JpaConverterJson;

import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "saved_address")
public class SavedAddress implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5118380731084279618L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "saved_address_id")
	private int savedAddressId;

	@Column(name = "label", length = 50)
	private String label;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="phone_number")
	private long phoneNumber;

	@Column(name = "address_line_1")
	private String addressLine1;
	
	@Column(name = "address_line_2")
	private String addressLine2;

	@Column(name = "city", length = 50)
	private String city;

	@Column(name = "state", length = 50)
	private String state;

	@Column(name = "country", length = 50)
	private String country;

	@NotNull
	@Column(name = "pincode")
	private String pincode;

	@Column(name = "created_date", length = 50)
	@JsonFormat(pattern = "YYYY-MM-DD HH:MM:SS")
	private Date createdDate;
	
	@Convert(converter = JpaConverterJson.class)
	@Column(name="other_data")
	private Object otherData;
	
	@Column(name="address_type")
	private String addressType;
	
	@Column(name="is_deleted")
	private boolean isDeleted;

	@Exclude
	@JsonBackReference(value = "user-saved-address")
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private UserInformation savedAddressUser;
}
