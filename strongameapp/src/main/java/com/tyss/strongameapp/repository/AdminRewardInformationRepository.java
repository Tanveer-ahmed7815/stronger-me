package com.tyss.strongameapp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.entity.AdminRewardDetails;

public interface AdminRewardInformationRepository extends JpaRepository<AdminRewardDetails, Integer > {

	@Query(value="select admin_reward_coins from admin_reward_details where admin_reward_id=?1", nativeQuery = true)
	double getAdminRewardCoin(int adminRewardId);

	@Transactional
	@Modifying
	@Query(value="update admin_reward_details set admin_reward_coins=?1 where admin_reward_id=?2",nativeQuery = true)
	void updateAdminReward(double adminBalanceCoin, int adminRewardId);


}
