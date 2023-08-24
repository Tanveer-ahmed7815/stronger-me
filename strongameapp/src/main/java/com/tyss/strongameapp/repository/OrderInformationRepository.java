package com.tyss.strongameapp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tyss.strongameapp.entity.OrderInformation;

public interface OrderInformationRepository extends JpaRepository<OrderInformation, Integer> {

	@Transactional
	@Modifying
	@Query(value = "update order_information set user_id=:userId where order_id=:orderId", nativeQuery = true)
	void updateOrder(@Param("orderId") int orderId, @Param("userId") int userId);

}