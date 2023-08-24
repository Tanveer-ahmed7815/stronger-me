package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.entity.PackageDetails;

public interface PackageDetailsRepository extends JpaRepository<PackageDetails, Integer>{

	@Query(value="select * from package_details where package_type='Regular'",nativeQuery = true)
	List<PackageDetails> findRegularPackages();

	@Query(value="select * from package_details where package_type='Add-On'",nativeQuery = true)
	List<PackageDetails> getAddOn();

	@Query(value="select * from package_details where package_type='Popular'",nativeQuery = true)
	List<PackageDetails> findPoluparPackages();

}
