package com.tyss.strongameapp.entity;

import java.io.Serializable;

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
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Repository
@Entity
@Data
@Table(name = "transformation_like_details")
public class TransformationLikeDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	public TransformationLikeDetails() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transformation_like_id")
	private int transformationLikeId;

	@Column(name = "transformation_like")
	private boolean like;

	@Exclude
	@JsonBackReference(value = "user-transformationlike")
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserInformation transformationLikeUser;

	@Exclude
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "transformation_id")
	private TransformationDetails transformationLike;

}
