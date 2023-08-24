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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Data
@Entity
@Table(name = "remaind_session")
public class RemaindSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5765065635802119996L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "remaind_session_id")
	private int remaindSessionId;

	@Column(name = "remainder_flag")
	private Boolean remainderFlag;

	@Exclude
	@JsonBackReference(value = "user-remaindSession")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserInformation remaindSessionUser;

	@Column(name = "session_id")
	private int sessionId;

	public RemaindSession() {
		super();
	}

	public RemaindSession(int remaindSessionId, Boolean remainderFlag, UserInformation remaindSessionUser,
			int sessionId) {
		super();
		this.remaindSessionId = remaindSessionId;
		this.remainderFlag = remainderFlag;
		this.remaindSessionUser = remaindSessionUser;
		this.sessionId = sessionId;
	}

}
