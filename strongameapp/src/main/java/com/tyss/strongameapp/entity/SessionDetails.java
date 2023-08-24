package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
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
@Table(name = "session_details")
public class SessionDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "session_id")
	private int sessionId;

	@Column(name = "session_link")
	private String sessionLink;

	@Column(name = "session_name")
	private String sessionName;

	@Column(name = "session_date")
	private Date sessionDate;

	@Column(name = "session_time")
	private Time sessionTime;

	@Column(name = "session_duration")
	private double sessionDuration;

	@Column(name = "slots_available")
	private int slotsAvailable;

	@Exclude
	@JsonBackReference(value = "coach-session")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "coach_for_session_id")
	private CoachDetails coachForSession;

	@Exclude
	@JsonBackReference
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "userSessions")
	private List<UserInformation> sessionUsers;

	public SessionDetails() {
		super();
	}

}
