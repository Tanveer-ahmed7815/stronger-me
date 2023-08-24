package com.tyss.strongameapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tyss.strongameapp.entity.ProductInformation;


public interface ProductInformationRepository extends JpaRepository<ProductInformation, Integer>{

	@Query("SELECT p FROM ProductInformation p WHERE p.productName = ?1")
	public Optional<ProductInformation> findProductByName(String name);

	@Query("SELECT p FROM ProductInformation p WHERE p.productId = ?1")
	public ProductInformation getProductById(int productId);

	@Query(value="select * FROM product_information",nativeQuery = true)
	public List<ProductInformation> filterProductByCoin();

	@Query(value="select * FROM product_information order by product_name",nativeQuery = true)
	public List<ProductInformation> filterProductByName();

	@Query(value="select * FROM product_information order by product_name desc",nativeQuery = true)
	public List<ProductInformation> filterProductByNameDesc();

	@Query(value="update product_information set strongame_db.order_information.order_id=:orderId where product_information.product_id=:productId ",nativeQuery = true)
	public void update(int orderId, int productId);

	@Query(value="SELECT * FROM product_information", nativeQuery = true)
	public List<ProductInformation> findAllProducts();

    public List<ProductInformation> findByProductTypeAndIsDeleted(String productType,boolean isDeleted);
    
    ProductInformation findByProductIdAndIsDeleted(int productId,boolean isDeleted);
	
}
