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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "package_details")
public class PackageDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7314406573790949717L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "package_id")
	private int packageId;

	@Column(name = "package_name")
	private String packageName;

	@Column(name = "package_duration")
	private double packageDuration;

	@Column(name = "actual_price")
	private double actualPrice;

	@Column(name = "offer_percentage")
	private double offerPercentage;

	@Column(name = "package_type")
	private String packageType;

	@Column(name = "package_icon")
	private String packageIcon;

	@Exclude
	@JsonBackReference(value = "tagline-package")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "tagline_details_id")
	private TaglineDetails packageTagline;

	@Exclude
	@JsonBackReference
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "userPackages")
	private List<UserInformation> packageUsers;

	public PackageDetails() {
		super();
	}

	public PackageDetails(int packageId, String packageName, double packageDuration, double actualPrice,
			double offerPercentage, String packageType, String packageIcon, List<UserInformation> packageUsers) {
		super();
		this.packageId = packageId;
		this.packageName = packageName;
		this.packageDuration = packageDuration;
		this.actualPrice = actualPrice;
		this.offerPercentage = offerPercentage;
		this.packageType = packageType;
		this.packageIcon = packageIcon;
		this.packageUsers = packageUsers;
	}

}
