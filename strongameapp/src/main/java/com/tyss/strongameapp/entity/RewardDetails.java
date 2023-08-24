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
@Table(name = "rewards_details")
public class RewardDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	public RewardDetails() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reward_id")
	private int rewardId;

	@Column(name = "no_of_steps")
	private double noOfSteps;

	@Column(name = "reward_coins")
	private double rewardCoins;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="user_id")
//	private UserInformation user;

//	public UserInformation getUser() {
//		return user;
//	}
//
//	public void setUser(UserInformation user) {
//		this.user = user;
//	}

}
