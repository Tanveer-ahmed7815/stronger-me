package com.tyss.strongameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.strongameapp.entity.MyOrderDetails;

public interface MyOrderDetailsRepository extends JpaRepository<MyOrderDetails, Integer> {

	// @Query(value = "select * from myorder_details where user_id=?1", nativeQuery
	// = true)
	// List<MyOrderDetails> getMyOrder(Integer userId);

	// List<MyOrderDetails> findByUserMyOrder(UserInformation userEntity);

}
