package com.tyss.strongameapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tyss.strongameapp.entity.CartProduct;
import com.tyss.strongameapp.entity.ProductAccessoryVariant;
import com.tyss.strongameapp.entity.ProductClothVariant;
import com.tyss.strongameapp.entity.ProductSupplementVariant;
import com.tyss.strongameapp.entity.UserInformation;

public interface CartProductRepo extends JpaRepository<CartProduct, Integer> {

	List<CartProduct> findAllByProductAccessoryAndCartProudutUser(ProductAccessoryVariant productAccessoryVariant,
			UserInformation userInformation);

	List<CartProduct> findAllByProductSupplementAndCartProudutUser(ProductSupplementVariant productSupplementVariant,
			UserInformation userInformation);

	List<CartProduct> findAllByProductClothAndCartProudutUser(ProductClothVariant productClothVariant,
			UserInformation userInformation);

}
