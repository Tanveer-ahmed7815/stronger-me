package com.tyss.strongameapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode.Exclude;

@Entity
@Data
@Table(name = "plan_details")
public class PlanInformation  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6341899404965671009L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_id")
	private int planId;

	@Column(name = "plan_name")
	private String planName;

	@Column(name = "plan_details", length = 999)
	private String planDetails;
	
	@Column(name="no_of_weeks")
	private double noOfWeeks;
	
	@Column(name = "plan_price")
	private double planPrice;
	
	@Exclude
    @JsonBackReference(value= "coach-plan")
	@ManyToMany(cascade = CascadeType.PERSIST,mappedBy = "coachPlans")
    private List<CoachDetails> planCoaches;
	
	@Exclude
    @JsonBackReference
	@ManyToMany(cascade = CascadeType.PERSIST,mappedBy = "userPlan")
	private List<UserInformation> planUsers;
	
	public PlanInformation() {
		super();
	}

	public PlanInformation(int planId, String planName, String planDetails, double noOfWeeks, double planPrice) {
		super();
		this.planId = planId;
		this.planName = planName;
		this.planDetails = planDetails;
		this.noOfWeeks = noOfWeeks;
		this.planPrice = planPrice;
	}
	
}
