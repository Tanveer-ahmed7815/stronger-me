package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tyss.strongameapp.entity.ProductAccessoryVariant;
import com.tyss.strongameapp.entity.ProductInformation;


@Repository
public interface ProductAccessoryVariantRepo extends JpaRepository<ProductAccessoryVariant, Integer>{
	
	List<ProductAccessoryVariant> findByAccessoryProductAndColorAndIsDeleted(ProductInformation accessoryProduct, String color, boolean isDeleted);

	
	List<ProductAccessoryVariant> findByAccessoryProductAndColorAndUnitAndSize(ProductInformation accessoryProduct, String color, String unit, double size);

}
