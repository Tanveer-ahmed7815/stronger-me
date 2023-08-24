package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tyss.strongameapp.entity.ProductClothVariant;
import com.tyss.strongameapp.entity.ProductInformation;

@Repository
public interface ProductClothVariantRepo extends JpaRepository<ProductClothVariant, Integer>{
	
	List<ProductClothVariant> findByClothProductAndColorAndIsDeleted(ProductInformation clothProduct, String color, boolean isDeleted);
	List<ProductClothVariant> findByClothProductAndColorAndSize(ProductInformation clothProduct, String color, String size);

}
