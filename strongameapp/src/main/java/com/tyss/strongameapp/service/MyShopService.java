package com.tyss.strongameapp.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.internet.AddressException;

import com.tyss.strongameapp.dto.AdvertisementInformationDto;
import com.tyss.strongameapp.dto.GetProductByVariantDTO;
import com.tyss.strongameapp.dto.MyShopDTO;
import com.tyss.strongameapp.dto.ProductInfoDTO;
import com.tyss.strongameapp.dto.ProductTypeDTO;
import com.tyss.strongameapp.dto.VariantTypeDTO;
import com.tyss.strongameapp.entity.UserInformation;

/**
 * MyShopService is implemented by MyShopServiceImple class which is used to
 * display the content of my shop page
 * 
 * @author Sushma Guttal
 *
 */
public interface MyShopService {


	/**
	 * This method is implemented by its implementation class, to fetch the total
	 * coins of specified user.
	 * 
	 * @param userEntity2
	 * @return double
	 */
	double getCoin(UserInformation userEntity2);

	/**
	 * This method is implemented by its implementation class, to fetch list of
	 * advertisements
	 * 
	 * @return List<AdvertisementInformationDto>
	 */
	List<AdvertisementInformationDto> getAdvertisements();

	/**
	 * This method is implemented by its implementation class, to send email to user
	 * 
	 * @param email
	 * @param name
	 * @param string
	 * @throws NoSuchProviderException
	 * @throws MessagingException
	 * @throws AddressException
	 */
	void sendMail(String email, String name, String string) throws MessagingException;

	/**
	 * This method is implemented by its implementation class, to fetch shopping
	 * banner and advertisements
	 * 
	 * @return AddsAndBannersDto
	 */
	com.tyss.strongameapp.dto.AddsAndBannersDto getAddsBanners();

	List<MyShopDTO> sortProductsByPrice(String productType,String type);

	List<MyShopDTO> getAllProducts(String type);

	ProductInfoDTO getProductById(int productId);

	ProductInfoDTO getProductByVariant(GetProductByVariantDTO getProductByVariantDTO);

	List<MyShopDTO> sortProductsByCoin(String productType,String type);

	List<MyShopDTO> sortProductsByName(String productType,String type);
	
	VariantTypeDTO getProductFilters(String productType);
	
	List<MyShopDTO> getFilteredProducts(String productType, VariantTypeDTO variantTypeDTO);
	
	List<ProductTypeDTO> getProductTypes();

}// End of MyShopService interface.
