package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tyss.strongameapp.entity.ProductInformation;
import com.tyss.strongameapp.entity.ProductSupplementVariant;

@Repository
public interface ProductSupplementVariantRepo extends JpaRepository<ProductSupplementVariant, Integer>{
	
	List<ProductSupplementVariant> findBySupplementProductAndFlavourAndIsDeleted(ProductInformation supplementProduct, String flavour, boolean isDeleted);
	List<ProductSupplementVariant> findBySupplementProductAndFlavourAndUnitAndSize(ProductInformation supplementProduct, String flavour, String unit, double size);

}
