package com.tyss.strongameapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tyss.strongameapp.entity.SavedAddress;

public interface SavedAddressRepository extends JpaRepository<SavedAddress, Integer> {

	@Modifying
	@Query("delete from SavedAddress s where s.savedAddressId= :savedAddressId")
	void deleteById(@Param("savedAddressId") int savedAddressId);
}
