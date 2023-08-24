package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyss.strongameapp.util.JpaConverterJson;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Data
@Entity
@Table(name = "recent_searched_address")
public class RecentSearchedAddress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 102987133579188336L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "searched_id")
	private int searchedId;

	@Column(name = "address")
	private String address;

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
	@Column(name = "other_data")
	private Object otherData;

	@Exclude
	@JsonBackReference(value = "user-searched-address")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserInformation searchedAddressUser;

}
