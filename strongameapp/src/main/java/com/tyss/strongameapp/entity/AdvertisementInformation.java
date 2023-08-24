package com.tyss.strongameapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "advertisement_information")
public class AdvertisementInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "advertisement_id")
	private int advertisementId;

	@Column(name = "advertisement_description", length = 999)
	private String advertisementDescription;

	@Column(name = "advertisement_image")
	private String advertisementImage;

}