package com.tyss.strongameapp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tyss.strongameapp.entity.RewardDetails;

public interface RewardDetailsRepository extends JpaRepository<RewardDetails, Integer> {
	@Query("select rewardCoins from RewardDetails where rewardId=?1" )
	public double getCoin(@Param("id") int id);

	@Transactional
	@Modifying
	@Query(value="update rewards_details set reward_coins = :balanceCoin where reward_id = :rewardId" ,nativeQuery=true)
	public int updateReward(@Param("balanceCoin") double balanceCoin,@Param("rewardId") int rewardId);

	@Transactional
	@Modifying
	@Query(value="update rewards_details set reward_coins=0 where reward_id=?1",nativeQuery = true)
	public void updateRewardToZero(int rewardId);

	
	
//	@Query(value="select count(1) from student where student_name = :name" ,nativeQuery=true )
//    Integer NumberOfStudent(@Param("name") String name);


}
