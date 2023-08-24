package com.tyss.strongameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.tyss.strongameapp.entity.RecentSearchedAddress;

public interface RecentAddressRepository extends JpaRepository<RecentSearchedAddress, Integer> {

	/* Limit is not working in jpql hence Native query is used */
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "delete from recent_searched_address where user_id= :userId order by searched_id asc LIMIT 1")
	void deleteFirstRecord(@Param("userId") int userId);

	@Modifying
	@Query("delete from RecentSearchedAddress r where r.searchedId= :recentAddressId")
	void deleteById(@Param("recentAddressId") int recentAddressId);

}
