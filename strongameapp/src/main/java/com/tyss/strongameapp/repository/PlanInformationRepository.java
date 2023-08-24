package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tyss.strongameapp.entity.PlanInformation;

public interface PlanInformationRepository extends JpaRepository<PlanInformation, Integer> {

	@Query("SELECT P FROM PlanInformation P where planId=?1")
	PlanInformation getPlanById(int planId);

	
	@Query(value="SELECT p.plan_id, p.no_of_weeks, p.plan_details, p.plan_name, p.plan_price FROM plan_details p join\r\n"
			+ "user_plan up on p.plan_id=up.plan_id join user_information u \r\n"
			+ "on up.user_id=:userId",nativeQuery = true)
	public List<PlanInformation> getPlan(@Param("userId") int userId);

}
