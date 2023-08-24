package com.tyss.strongameapp.controller;

import static com.tyss.strongameapp.constants.UserConstants.USER_NOT_FOUND;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyss.strongameapp.constants.CartConstants;
import com.tyss.strongameapp.constants.MyShopConstants;
import com.tyss.strongameapp.dto.AddsAndBannersDto;
import com.tyss.strongameapp.dto.GetProductByVariantDTO;
import com.tyss.strongameapp.dto.MyShopDTO;
import com.tyss.strongameapp.dto.ProductInfoDTO;
import com.tyss.strongameapp.dto.ProductTypeDTO;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.dto.VariantTypeDTO;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.repository.UserInformationRepository;
import com.tyss.strongameapp.service.MyShopService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Sushma Guttal My shop controller is used to display my shop page.
 *
 */
@Slf4j
@CrossOrigin(origins = { "capacitor://localhost", "ionic://localhost", "http://localhost", "http://localhost:8080",
		"http://localhost:8100", "https://strongame.web.app", "https://strongermeuser.herokuapp.com" })
@RestController
@RequestMapping("/myshop")
public class MyShopController {

	/**
	 * This field is used to invoke business layer methods.
	 */
	@Autowired
	private MyShopService myShopService;

	@Autowired
	private UserInformationRepository userRepo;

	@GetMapping("getcoin/{userId}")
	public ResponseEntity<ResponseDto> getCoin(@PathVariable int userId) {
		ResponseDto responseDTO = new ResponseDto();
		Optional<UserInformation> userEntity = userRepo.findById(userId);
		if (!userEntity.isPresent()) {
			log.error(USER_NOT_FOUND);
			responseDTO.setError(true);
			responseDTO.setData(USER_NOT_FOUND);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		}
		UserInformation userEntity2 = userEntity.get();
		double coins = myShopService.getCoin(userEntity2);
		// create and return ResponseEntity object
		if (coins == 0) {
			log.error("No Coins Available for You");
			responseDTO.setError(true);
			responseDTO.setData("No Coins Available for You");
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		} else {
			log.debug("Coins are displaying");
			responseDTO.setError(false);
			responseDTO.setData(coins);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}/// End of get coin method

	@GetMapping("getadds")
	public ResponseEntity<ResponseDto> getAddsBanners() {
		AddsAndBannersDto dto = myShopService.getAddsBanners();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error("No Advertisements and Banners Available for You");
			responseDTO.setError(true);
			responseDTO.setData("No Advertisements and Banners Available for You");
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug("Advertisements and Banners are Fetched");
			responseDTO.setError(false);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of getAddsBanners method

	/**
	 * This method is used to fetch all products based on type
	 * 
	 * @param type
	 * 
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation(value = "This API is to fetch all products based on type")
	@ApiResponses(value = { @ApiResponse(code = 404, message = MyShopConstants.NO_PRODUCT_AVAILABLE),
			@ApiResponse(code = 200, message = MyShopConstants.PRODUCTS_FETCHED) })
	@GetMapping("/get/{type}")
	public ResponseEntity<ResponseDto> getAllProducts(@PathVariable String type) {
		List<MyShopDTO> dto = myShopService.getAllProducts(type);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error(MyShopConstants.NO_PRODUCT_AVAILABLE);
			responseDTO.setError(true);
			responseDTO.setData(dto);
			responseDTO.setMessage(MyShopConstants.NO_PRODUCT_AVAILABLE);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug(MyShopConstants.PRODUCTS_FETCHED);
			responseDTO.setError(false);
			responseDTO.setData(dto);
			responseDTO.setMessage(MyShopConstants.PRODUCTS_FETCHED);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of sortProductsByPrice

	/**
	 * This method is used to fetch single product details by its id.
	 * 
	 * @param productid
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation(value = "This API is to fetch single product details by its id")
	@ApiResponses(value = { @ApiResponse(code = 404, message = CartConstants.PRODUCT_NOT_AVAILABLE),
			@ApiResponse(code = 200, message = MyShopConstants.PRODUCT_FETCHED) })
	@GetMapping("get/product/{productId}")
	public ResponseEntity<ResponseDto> getProductById(@PathVariable int productId) {
		ProductInfoDTO dto = myShopService.getProductById(productId);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error(CartConstants.PRODUCT_NOT_AVAILABLE);
			responseDTO.setError(true);
			responseDTO.setMessage(CartConstants.PRODUCT_NOT_AVAILABLE);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug(MyShopConstants.PRODUCT_FETCHED);
			responseDTO.setError(false);
			responseDTO.setData(dto);
			responseDTO.setMessage(MyShopConstants.PRODUCT_FETCHED);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of get product by id method

	/**
	 * This method is used to fetch product and its second variants based on id and
	 * selected first variant
	 * 
	 * @param getProductByVariantDTO
	 * 
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation(value = "This API is to fetch product and its second variants based on id and selected first variant")
	@ApiResponses(value = { @ApiResponse(code = 404, message = CartConstants.PRODUCT_NOT_AVAILABLE),
			@ApiResponse(code = 200, message = MyShopConstants.PRODUCT_FETCHED) })
	@PostMapping("get/product/by-variant")
	public ResponseEntity<ResponseDto> getProductDetails(@RequestBody GetProductByVariantDTO getProductByVariantDTO) {
		ProductInfoDTO dto = myShopService.getProductByVariant(getProductByVariantDTO);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error(CartConstants.PRODUCT_NOT_AVAILABLE);
			responseDTO.setError(true);
			responseDTO.setMessage(CartConstants.PRODUCT_NOT_AVAILABLE);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug(MyShopConstants.PRODUCT_FETCHED);
			responseDTO.setError(false);
			responseDTO.setData(dto);
			responseDTO.setMessage(MyShopConstants.PRODUCT_FETCHED);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of get product by id method

	/**
	 * This method is used to sort the products in ascending/descending order based
	 * on coins.
	 * 
	 * @param productType
	 * @param type
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation(value = "This API is to sort the products in ascending/descending order based on coins")
	@ApiResponses(value = { @ApiResponse(code = 404, message = MyShopConstants.NO_PRODUCT_AVAILABLE),
			@ApiResponse(code = 200, message = MyShopConstants.PRODUCTS_FETCHED) })
	@GetMapping("/sort/by-coins/{productType}/{type}")
	public ResponseEntity<ResponseDto> sortProductsByCoin(@PathVariable String productType,
			@PathVariable String type) {
		List<MyShopDTO> dto = myShopService.sortProductsByCoin(productType, type);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error(MyShopConstants.NO_PRODUCT_AVAILABLE);
			responseDTO.setError(true);
			responseDTO.setMessage(MyShopConstants.NO_PRODUCT_AVAILABLE);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug(MyShopConstants.PRODUCTS_FETCHED);
			responseDTO.setError(false);
			responseDTO.setData(dto);
			responseDTO.setMessage(MyShopConstants.PRODUCTS_FETCHED);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of filter product by coins method

	/**
	 * This method is used to sort the products in ascending/descending order based
	 * on name.
	 * 
	 * @param productType
	 * @param type
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation(value = "This API is used to sort the products in ascending/descending order based on name")
	@ApiResponses(value = { @ApiResponse(code = 404, message = MyShopConstants.NO_PRODUCT_AVAILABLE),
			@ApiResponse(code = 200, message = MyShopConstants.PRODUCTS_FETCHED) })
	@GetMapping("/sort/by-name/{productType}/{type}")
	public ResponseEntity<ResponseDto> sortProductsByName(@PathVariable String productType,
			@PathVariable String type) {
		List<MyShopDTO> dto = myShopService.sortProductsByName(productType, type);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error(MyShopConstants.NO_PRODUCT_AVAILABLE);
			responseDTO.setError(true);
			responseDTO.setMessage(MyShopConstants.NO_PRODUCT_AVAILABLE);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug(MyShopConstants.PRODUCTS_FETCHED);
			responseDTO.setError(false);
			responseDTO.setData(dto);
			responseDTO.setMessage(MyShopConstants.PRODUCTS_FETCHED);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of filter product by name method

	/**
	 * This method is used to sort the products in ascending/descending order based
	 * on price
	 * 
	 * @param productType
	 * @param type
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation(value = "This API is to sort the products in ascending/descending order based on price")
	@ApiResponses(value = { @ApiResponse(code = 404, message = MyShopConstants.NO_PRODUCT_AVAILABLE),
			@ApiResponse(code = 200, message = MyShopConstants.PRODUCTS_FETCHED) })
	@GetMapping("/sort/by-price/{productType}/{type}")
	public ResponseEntity<ResponseDto> sortProductsByPrice(@PathVariable String productType,
			@PathVariable String type) {
		List<MyShopDTO> dto = myShopService.sortProductsByPrice(productType, type);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto.isEmpty()) {
			log.error(MyShopConstants.NO_PRODUCT_AVAILABLE);
			responseDTO.setError(true);
			responseDTO.setMessage(MyShopConstants.NO_PRODUCT_AVAILABLE);
			responseDTO.setData(dto);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug(MyShopConstants.PRODUCTS_FETCHED);
			responseDTO.setError(false);
			responseDTO.setData(dto);
			responseDTO.setMessage(MyShopConstants.PRODUCTS_FETCHED);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of sortProductsByPrice

	/**
	 * This method is used to to fetch filter types of the product
	 * 
	 * @param productType
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation(value = "This API is to fetch filter types of the product")
	@ApiResponses(value = { @ApiResponse(code = 404, message = MyShopConstants.NO_VARIANT_AVAILABLE),
			@ApiResponse(code = 200, message = MyShopConstants.VARIANT_FETCHED) })
	@GetMapping("/filter/type/{productType}")
	public ResponseEntity<ResponseDto> getProductFilters(@PathVariable String productType) {
		VariantTypeDTO dto = myShopService.getProductFilters(productType);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dto == null) {
			log.error(MyShopConstants.NO_VARIANT_AVAILABLE);
			responseDTO.setError(true);
			responseDTO.setData(dto);
			responseDTO.setMessage(MyShopConstants.NO_VARIANT_AVAILABLE);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug(MyShopConstants.VARIANT_FETCHED);
			responseDTO.setError(false);
			responseDTO.setData(dto);
			responseDTO.setMessage(MyShopConstants.VARIANT_FETCHED);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of getCoachfilters method

	/**
	 * This method is used to fetch filtered products
	 * 
	 * @param productType
	 * @param variantTypeDTO
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation(value = "This API is to fetch filtered products")
	@ApiResponses(value = { @ApiResponse(code = 404, message = MyShopConstants.NO_PRODUCT_AVAILABLE),
			@ApiResponse(code = 200, message = MyShopConstants.PRODUCTS_FETCHED) })
	@PostMapping("/filter/product/{productType}")
	public ResponseEntity<ResponseDto> getFilteredProducts(@PathVariable String productType,
			@RequestBody VariantTypeDTO variantTypeDTO) {
		List<MyShopDTO> dtos = myShopService.getFilteredProducts(productType, variantTypeDTO);
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dtos.isEmpty()) {
			log.error(MyShopConstants.NO_PRODUCT_AVAILABLE);
			responseDTO.setError(true);
			responseDTO.setData(dtos);
			responseDTO.setMessage(MyShopConstants.NO_PRODUCT_AVAILABLE);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug(MyShopConstants.PRODUCTS_FETCHED);
			responseDTO.setError(false);
			responseDTO.setData(dtos);
			responseDTO.setMessage(MyShopConstants.PRODUCTS_FETCHED);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of getCoachfilters method

	/**
	 * This method is used to fetch product types
	 * 
	 * @return ResponseEntity<ResponseDto>
	 */
	@ApiOperation(value = "This API is to fetch product types")
	@ApiResponses(value = { @ApiResponse(code = 404, message = MyShopConstants.NO_PRODUCT_TYPE_AVAILABLE),
			@ApiResponse(code = 200, message = MyShopConstants.PRODUCT_TYPE_FETCHED) })
	@GetMapping("/product/types")
	public ResponseEntity<ResponseDto> getProductTypes() {
		List<ProductTypeDTO> dtos = myShopService.getProductTypes();
		ResponseDto responseDTO = new ResponseDto();
		// create and return ResponseEntity object
		if (dtos.isEmpty()) {
			log.error(MyShopConstants.NO_PRODUCT_TYPE_AVAILABLE);
			responseDTO.setError(true);
			responseDTO.setData(dtos);
			responseDTO.setMessage(MyShopConstants.NO_PRODUCT_TYPE_AVAILABLE);
			return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
		} else {
			log.debug(MyShopConstants.PRODUCT_TYPE_FETCHED);
			responseDTO.setError(false);
			responseDTO.setData(dtos);
			responseDTO.setMessage(MyShopConstants.PRODUCT_TYPE_FETCHED);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		}
	}// End of getCoachfilters method

}// End of My Shop Controller.
