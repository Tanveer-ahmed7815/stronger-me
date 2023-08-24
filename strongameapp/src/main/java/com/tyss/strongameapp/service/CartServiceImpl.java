package com.tyss.strongameapp.service;

import static com.tyss.strongameapp.constants.CartConstants.OUT_OF_STOCK;
import static com.tyss.strongameapp.constants.CartConstants.PRODUCT_REMOVED;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.constants.CartConstants;
import com.tyss.strongameapp.dto.CartDto;
import com.tyss.strongameapp.dto.CartProductDto;
import com.tyss.strongameapp.dto.ProductInformationDto;
import com.tyss.strongameapp.dto.ProductSizeStockDto;
import com.tyss.strongameapp.dto.UpdateCartProductDto;
import com.tyss.strongameapp.entity.CartProduct;
import com.tyss.strongameapp.entity.ProductAccessoryVariant;
import com.tyss.strongameapp.entity.ProductClothVariant;
import com.tyss.strongameapp.entity.ProductInformation;
import com.tyss.strongameapp.entity.ProductSupplementVariant;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.exception.ProductException;
import com.tyss.strongameapp.exception.UserNotExistException;
import com.tyss.strongameapp.repository.CartProductRepo;
import com.tyss.strongameapp.repository.ProductAccessoryVariantRepo;
import com.tyss.strongameapp.repository.ProductClothVariantRepo;
import com.tyss.strongameapp.repository.ProductInformationRepository;
import com.tyss.strongameapp.repository.ProductSupplementVariantRepo;
import com.tyss.strongameapp.repository.UserInformationRepository;

/**
 * This is the service implementation class for CartService interface. Here you
 * find implementation for saving, updating, fetching and deleting the cart
 * product
 * 
 * @author Afridi
 * 
 */
@Service
public class CartServiceImpl implements CartService {

	/**
	 * private static final String SIZEPATTERN =
	 * "^X{0,2}M{0,1}S{0,1}L{0,1}K{0,1}G{0,1}N{0,1}A{0,1}$";
	 * 
	 * /** This field is used for invoking persistence layer methods
	 */
	@Autowired
	private ProductInformationRepository productRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	private UserInformationRepository userRepo;

	/**
	 * This field is used for invoking persistence layer methods
	 */
	@Autowired
	private CartProductRepo cartProductRepo;

	@Autowired
	private ProductClothVariantRepo productClothVariantRepo;

	@Autowired
	private ProductSupplementVariantRepo productSupplementVariantRepo;

	/**
	 * This field is used for invoking myShopService methods to fetch coins
	 */
	@Autowired
	private MyShopService myShopService;

	@Autowired
	private ProductAccessoryVariantRepo productAccessoryVariantRepo;

	ProductInformationDto productInformationDto;

	/**
	 * This method is implemented to save Cart Product
	 * 
	 * @param quantityDto
	 * @param userId
	 * @return ProductInformationDto
	 */
	@Override
	@Transactional
	public String addToCart(CartProductDto cartProductDto, int userId, int productId) {
		UserInformation userInformation = userRepo.findById(userId).orElseThrow(UserNotExistException::new);
		ProductInformation product = productRepo.findById(productId)
				.orElseThrow(() -> new ProductException("Product Not found"));

		if (product.isDeleted()) {
			throw new ProductException(PRODUCT_REMOVED);
		}
		CartProduct cartProduct = new CartProduct();

		if (product.getProductType().equalsIgnoreCase("ACCESSORY")) {

			return addAccessoryProduct(userInformation, cartProduct, cartProductDto);

		} else if (product.getProductType().equalsIgnoreCase("CLOTH")) {

			return addClothProduct(userInformation, cartProduct, cartProductDto);

		} else if (product.getProductType().equalsIgnoreCase("SUPPLEMENT")) {

			return addSupplementProduct(userInformation, cartProduct, cartProductDto);

		}

		else {

			throw new ProductException("Product Variant Not found");
		}

	}

	private String addSupplementProduct(UserInformation userInformation, CartProduct cartProduct,
			CartProductDto cartProductDto) {
		ProductSupplementVariant productSupplementVariant = productSupplementVariantRepo
				.findById(cartProductDto.getVariantId())
				.orElseThrow(() -> new ProductException(CartConstants.PRODUCT_VARIANT_NOT_AVAILABLE));

		if (productSupplementVariant.isDeleted()) {
			throw new ProductException(CartConstants.PRODUCT_VARIANT_NOT_AVAILABLE);
		}

		if (cartProductDto.getQuantity() > productSupplementVariant.getQuantity()) {
			throw new ProductException(OUT_OF_STOCK);
		}

		List<CartProduct> userCartProducts = userInformation.getCartProducts();
		double coins = productSupplementVariant.getCoins();

		double userCoinInCart = userCoinInCart(userCartProducts) + cartProductDto.getUsedCoins();

		double userTotalCoins = myShopService.getCoin(userInformation);

		if (cartProductDto.getUsedCoins() > coins) {

			throw new ProductException(CartConstants.COINS_1 + coins + CartConstants.COINS_2);
		}

		List<CartProduct> cartProducts = cartProductRepo
				.findAllByProductSupplementAndCartProudutUser(productSupplementVariant, userInformation);
		CartProduct cartSupplementoryProduct = cartProducts.stream().filter(x -> !x.isOrderd()).findFirst()
				.orElse(null);
		if (cartSupplementoryProduct != null) {

			cartSupplementoryProduct.setUsedCoins(cartProductDto.getUsedCoins());
			return CartConstants.PRODUCT_REPEAT_IN_CART;

		}

		if (userCoinInCart > userTotalCoins) {

			throw new ProductException(CartConstants.INSUFFICENT_COINS + userTotalCoins);
		}

		double priceToBePaid;
		double actualPrice = productSupplementVariant.getPrice();
		double discount = productSupplementVariant.getDiscount();
		priceToBePaid = (actualPrice - (discount * actualPrice / 100)) - cartProductDto.getUsedCoins();
		cartProduct.setProductSupplement(productSupplementVariant);
		cartProduct.setUsedCoins(cartProductDto.getUsedCoins());
		cartProduct.setCartProudutUser(userInformation);
		cartProduct.setToBePaid(priceToBePaid);
		cartProduct.setQuantity(1);
		cartProductRepo.save(cartProduct);
		return CartConstants.SUCCESSFULLY_ADD_TO_CART;
	}

	private String addClothProduct(UserInformation userInformation, CartProduct cartProduct,
			CartProductDto cartProductDto) {

		ProductClothVariant productClothVariant = productClothVariantRepo.findById(cartProductDto.getVariantId())
				.orElseThrow(() -> new ProductException(CartConstants.PRODUCT_VARIANT_NOT_AVAILABLE));

		if (productClothVariant.isDeleted()) {
			throw new ProductException(CartConstants.PRODUCT_VARIANT_NOT_AVAILABLE);
		}

		if (cartProductDto.getQuantity() > productClothVariant.getQuantity()) {
			throw new ProductException(OUT_OF_STOCK);
		}

		List<CartProduct> userCartProducts = userInformation.getCartProducts();

		double userCoinInCart = userCoinInCart(userCartProducts) + cartProductDto.getUsedCoins();

		double userTotalCoins = myShopService.getCoin(userInformation);

		double coins = productClothVariant.getCoins();

		if (cartProductDto.getUsedCoins() > coins) {

			throw new ProductException(CartConstants.COINS_1 + coins + CartConstants.COINS_2);
		}

		List<CartProduct> cartProducts = cartProductRepo.findAllByProductClothAndCartProudutUser(productClothVariant,
				userInformation);

		CartProduct cartClothProduct = cartProducts.stream().filter(x -> !x.isOrderd()).findFirst().orElse(null);
		if (cartClothProduct != null) {

			cartClothProduct.setUsedCoins(cartProductDto.getUsedCoins());
			return CartConstants.PRODUCT_REPEAT_IN_CART;

		}

		if (userCoinInCart > userTotalCoins) {

			throw new ProductException(CartConstants.INSUFFICENT_COINS + userTotalCoins);
		}

		double priceToBePaid;
		double actualPrice = productClothVariant.getPrice();
		double discount = productClothVariant.getDiscount();
		priceToBePaid = (actualPrice - (discount * actualPrice / 100)) - cartProductDto.getUsedCoins();
		cartProduct.setUsedCoins(cartProductDto.getUsedCoins());
		cartProduct.setProductCloth(productClothVariant);
		cartProduct.setCartProudutUser(userInformation);
		cartProduct.setToBePaid(priceToBePaid);
		cartProduct.setQuantity(1);
		cartProductRepo.save(cartProduct);

		return CartConstants.SUCCESSFULLY_ADD_TO_CART;
	}

	private String addAccessoryProduct(UserInformation userInformation, CartProduct cartProduct,
			CartProductDto cartProductDto) {

		ProductAccessoryVariant productAccessoryVariant = productAccessoryVariantRepo
				.findById(cartProductDto.getVariantId())
				.orElseThrow(() -> new ProductException(CartConstants.PRODUCT_VARIANT_NOT_AVAILABLE));

		if (productAccessoryVariant.isDeleted()) {
			throw new ProductException(CartConstants.PRODUCT_VARIANT_NOT_AVAILABLE);
		}

		if (cartProductDto.getQuantity() > productAccessoryVariant.getQuantity()) {
			throw new ProductException(OUT_OF_STOCK);
		}

		List<CartProduct> userCartProducts = userInformation.getCartProducts();

		double userCoinInCart = userCoinInCart(userCartProducts) + cartProductDto.getUsedCoins();

		double userTotalCoins = myShopService.getCoin(userInformation);

		double coins = productAccessoryVariant.getCoins();

		if (cartProductDto.getUsedCoins() > coins) {

			throw new ProductException(CartConstants.COINS_1 + coins + CartConstants.COINS_2);
		}

		List<CartProduct> cartProducts = cartProductRepo
				.findAllByProductAccessoryAndCartProudutUser(productAccessoryVariant, userInformation);

		CartProduct cartAccessoryProduct = cartProducts.stream().filter(x -> !x.isOrderd()).findFirst().orElse(null);
		if (cartAccessoryProduct != null) {

			cartAccessoryProduct.setUsedCoins(cartProductDto.getUsedCoins());
			return CartConstants.PRODUCT_REPEAT_IN_CART;

		}

		if (userCoinInCart > userTotalCoins) {

			throw new ProductException(CartConstants.INSUFFICENT_COINS + userTotalCoins);
		}

		double priceToBePaid;
		double actualPrice = productAccessoryVariant.getPrice();
		double discount = productAccessoryVariant.getDiscount();
		priceToBePaid = (actualPrice - (discount * actualPrice / 100)) - cartProductDto.getUsedCoins();
		cartProduct.setUsedCoins(cartProductDto.getUsedCoins());
		cartProduct.setProductAccessory(productAccessoryVariant);
		cartProduct.setCartProudutUser(userInformation);
		cartProduct.setToBePaid(priceToBePaid);
		cartProduct.setQuantity(1);
		cartProductRepo.save(cartProduct);

		return CartConstants.SUCCESSFULLY_ADD_TO_CART;

	}

	/**
	 * This method is implemented to get total number of coins used for cart
	 * products
	 * 
	 * @param userInformation
	 */
	public double userCoinInCart(List<CartProduct> cartProductList) {
		return cartProductList.stream().filter(x -> !x.isOrderd()).map(CartProduct::getUsedCoins).reduce(0.0,
				Double::sum);
	}

	/**
	 * This method is implemented to update Cart Product
	 * 
	 * @param updateCartProductDto
	 * @param userId
	 * @return UpdateCartProductDto
	 */
	@Override
	@Transactional
	public UpdateCartProductDto updateCart(UpdateCartProductDto dto, int userId) {

		UserInformation userInformation = userRepo.findById(userId).orElseThrow(UserNotExistException::new);

		ProductInformation product = productRepo.findById(dto.getProductId())
				.orElseThrow(() -> new ProductException(CartConstants.PRODUCT_VARIANT_NOT_AVAILABLE));
		if (product.isDeleted()) {
			throw new ProductException(PRODUCT_REMOVED);
		}

		if (userInformation.getCartProducts().isEmpty()) {
			throw new ProductException("Cart is Empty");
		}

		UpdateCartProductDto updateCartProductDto = new UpdateCartProductDto();

		CartProduct cartProduct = cartProductRepo.findById(dto.getCartProductId())
				.orElseThrow(() -> new ProductException("Cart Product Not found"));

		if (cartProduct.getProductAccessory() != null) {

			return updateAccessoryProduct(userInformation, product, dto, cartProduct, updateCartProductDto);

		} else if (cartProduct.getProductCloth() != null) {

			return updateClothProduct(userInformation, product, dto, cartProduct, updateCartProductDto);

		} else if (cartProduct.getProductSupplement() != null) {

			return updateSupplementProduct(userInformation, product, dto, cartProduct, updateCartProductDto);

		}

		else {
			throw new ProductException(CartConstants.PRODUCT_VARIANT_NOT_AVAILABLE);
		}

	}

	private UpdateCartProductDto updateSupplementProduct(UserInformation userInformation, ProductInformation product,
			UpdateCartProductDto dto, CartProduct cartProduct, UpdateCartProductDto updateCartProductDto) {
		if (userInformation.getCartProducts().stream().noneMatch(x -> x.getCartProductId() == dto.getCartProductId())
				|| product.getProductSupplementVariant().stream()
						.noneMatch(x -> x.getProductSupplementVariantId() == dto.getVariantId())) {
			throw new ProductException(CartConstants.UNABLE_TO_UPDATE);
		}

		ProductSupplementVariant productSupplementVariant = productSupplementVariantRepo.findById(dto.getVariantId())
				.orElseThrow(() -> new ProductException(CartConstants.PRODUCT_VARIANT_NOT_AVAILABLE));

		if (productSupplementVariant.isDeleted()) {
			throw new ProductException(CartConstants.PRODUCT_VARIANT_NOT_AVAILABLE);
		}

		if (dto.getQuantity() > productSupplementVariant.getQuantity()) {
			throw new ProductException(OUT_OF_STOCK);
		}

		double usedCoins = 0.0;
		if (productSupplementVariant.getCoins() < cartProduct.getUsedCoins()) {
			cartProduct.setUsedCoins(productSupplementVariant.getCoins());
			usedCoins = productSupplementVariant.getCoins();
		}
		cartProduct.setQuantity(dto.getQuantity());

		double priceToBePaid;

		double actualPrice = productSupplementVariant.getPrice();
		double discount = productSupplementVariant.getDiscount();

		priceToBePaid = dto.getQuantity() * ((actualPrice - (discount * actualPrice / 100)) - usedCoins);
		cartProduct.setToBePaid(priceToBePaid);

		cartProduct.setProductSupplement(productSupplementVariant);
		updateCartProductDto.setCartProductId(dto.getCartProductId());
		updateCartProductDto.setProductId(dto.getProductId());
		updateCartProductDto.setQuantity(dto.getQuantity());
		updateCartProductDto.setVariantId(dto.getVariantId());
		return updateCartProductDto;
	}

	private UpdateCartProductDto updateClothProduct(UserInformation userInformation, ProductInformation product,
			UpdateCartProductDto dto, CartProduct cartProduct, UpdateCartProductDto updateCartProductDto) {
		if (userInformation.getCartProducts().stream().noneMatch(x -> x.getCartProductId() == dto.getCartProductId())
				|| product.getClothVariants().stream().noneMatch(x -> x.getClothVariantId() == dto.getVariantId())) {
			throw new ProductException(CartConstants.UNABLE_TO_UPDATE);
		}

		ProductClothVariant productClothVariant = productClothVariantRepo.findById(dto.getVariantId())
				.orElseThrow(() -> new ProductException(CartConstants.PRODUCT_VARIANT_NOT_AVAILABLE));

		if (productClothVariant.isDeleted()) {

			throw new ProductException(CartConstants.PRODUCT_VARIANT_NOT_AVAILABLE);

		}

		if (dto.getQuantity() > productClothVariant.getQuantity()) {
			throw new ProductException(OUT_OF_STOCK);
		}

		double usedCoins = 0.0;
		if (productClothVariant.getCoins() < cartProduct.getUsedCoins()) {
			cartProduct.setUsedCoins(productClothVariant.getCoins());
			usedCoins = productClothVariant.getCoins();
		}
		cartProduct.setQuantity(dto.getQuantity());

		double priceToBePaid;

		double actualPrice = productClothVariant.getPrice();
		double discount = productClothVariant.getDiscount();

		priceToBePaid = dto.getQuantity() * ((actualPrice - (discount * actualPrice / 100)) - usedCoins);
		cartProduct.setToBePaid(priceToBePaid);

		cartProduct.setProductCloth(productClothVariant);

		updateCartProductDto.setCartProductId(dto.getCartProductId());
		updateCartProductDto.setProductId(dto.getProductId());
		updateCartProductDto.setQuantity(dto.getQuantity());
		updateCartProductDto.setVariantId(dto.getVariantId());
		return updateCartProductDto;
	}

	private UpdateCartProductDto updateAccessoryProduct(UserInformation userInformation, ProductInformation product,
			UpdateCartProductDto dto, CartProduct cartProduct, UpdateCartProductDto updateCartProductDto) {
		if (userInformation.getCartProducts().stream().noneMatch(x -> x.getCartProductId() == dto.getCartProductId())
				|| product.getAccessoryVariants().stream()
						.noneMatch(x -> x.getAccessoryVariantId() == dto.getVariantId())) {
			throw new ProductException(CartConstants.UNABLE_TO_UPDATE);
		}

		ProductAccessoryVariant productAccessoryVariant = productAccessoryVariantRepo.findById(dto.getVariantId())
				.orElseThrow(() -> new ProductException(CartConstants.PRODUCT_VARIANT_NOT_AVAILABLE));

		if (dto.getQuantity() > productAccessoryVariant.getQuantity()) {
			throw new ProductException(OUT_OF_STOCK);
		}

		double usedCoins = 0.0;
		if (productAccessoryVariant.getCoins() < cartProduct.getUsedCoins()) {
			cartProduct.setUsedCoins(productAccessoryVariant.getCoins());
			usedCoins = productAccessoryVariant.getCoins();
		}

		if (productAccessoryVariant.isDeleted()) {
			throw new ProductException(CartConstants.PRODUCT_VARIANT_NOT_AVAILABLE);
		}

		cartProduct.setQuantity(dto.getQuantity());

		double priceToBePaid;

		double actualPrice = productAccessoryVariant.getPrice();
		double discount = productAccessoryVariant.getDiscount();

		priceToBePaid = dto.getQuantity() * ((actualPrice - (discount * actualPrice / 100)) - usedCoins);
		cartProduct.setToBePaid(priceToBePaid);

		cartProduct.setProductAccessory(productAccessoryVariant);

		updateCartProductDto.setCartProductId(dto.getCartProductId());
		updateCartProductDto.setProductId(dto.getProductId());
		updateCartProductDto.setQuantity(dto.getQuantity());
		updateCartProductDto.setVariantId(dto.getVariantId());
		return updateCartProductDto;
	}

	/**
	 * This method is implemented to view Cart
	 * 
	 * @param userId
	 */
	@Override
	public CartDto viewCart(int userId) {
		UserInformation user = userRepo.findById(userId).orElseThrow(UserNotExistException::new);
		CartDto cartDto = new CartDto();
		cartDto.setUserId(userId);
		double finalCartPrice = 0.0;
		double actualCartPrice = 0.0;
		double totalDiscountPrice = 0.0;
		double discount;
		double savings;
		List<CartProduct> cartProducts = user.getCartProducts();
		if (cartProducts.isEmpty()) {
			return null;
		}
		List<ProductInformationDto> dtos = new ArrayList<>();
		for (CartProduct cartProduct : cartProducts) {
			if (!cartProduct.isOrderd()) {
				ProductAccessoryVariant productAccessory = cartProduct.getProductAccessory();
				ProductClothVariant productCloth = cartProduct.getProductCloth();
				ProductSupplementVariant productSupplement = cartProduct.getProductSupplement();
				productInformationDto = new ProductInformationDto();
				productInformationDto.setMessage("");
				if (productAccessory != null) {
					productInformationDto = accessoryToProductDto(productAccessory, cartProduct);
				} else if (productCloth != null) {
					productInformationDto = clothToProductDto(productCloth, cartProduct);
				} else if (productSupplement != null) {
					productInformationDto = supplementToProductDto(productSupplement, cartProduct);
				} else {
					throw new ProductException("Product Variant Not Found");
				}
				if (productInformationDto.isDeleted()) {
					cartProductRepo.deleteById(cartProduct.getCartProductId());
				}
				discount = productInformationDto.getDiscount();
				savings = ((discount * productInformationDto.getPrice()) / 100);
				BeanUtils.copyProperties(cartProduct, productInformationDto);
				productInformationDto.setCartProductId(cartProduct.getCartProductId());
				productInformationDto.setFinalPrice((productInformationDto.getPrice() - savings));
				dtos.add(productInformationDto);
				finalCartPrice += cartProduct.getToBePaid();
				actualCartPrice += productInformationDto.getPrice() * cartProduct.getQuantity();
				totalDiscountPrice += ((productInformationDto.getPrice() * cartProduct.getQuantity()) / 100) * discount;
			}
		}
		if (productInformationDto.getMessage().length() > 1) {
			productInformationDto.setMessage(productInformationDto.getMessage() + "variant has been Removed");
		}
		cartDto.setActualCartPrice(actualCartPrice);
		cartDto.setTotalDiscountPrice(totalDiscountPrice);
		cartDto.setFinalCartPrice(finalCartPrice);
		cartDto.setTotalUsedCoins(userCoinInCart(cartProducts));
		cartDto.setCartProduct(dtos);
		return cartDto;
	}

	/**
	 * if supplement variant is not null then copying all variant data into
	 * ProductInformationDto
	 * 
	 * @param productSupplement
	 * @param cartProduct
	 * @return
	 */
	private ProductInformationDto supplementToProductDto(ProductSupplementVariant productSupplement,
			CartProduct cartProduct) {
		List<ProductSizeStockDto> sizeStockDtos = new ArrayList<>();
		ProductInformation information = productSupplement.getSupplementProduct();
		if (information.isDeleted()) {
			cartProductRepo.deleteById(cartProduct.getCartProductId());
			productInformationDto.setMessage(productInformationDto.getMessage() + " | " + information.getProductName());
		}
		List<ProductSupplementVariant> list = information.getProductSupplementVariant().stream()
				.filter(x -> x.getFlavour().equalsIgnoreCase(productSupplement.getFlavour()))
				.collect(Collectors.toList());
		for (ProductSupplementVariant supplementVariant : list) {
			ProductSizeStockDto sizeStockDto = new ProductSizeStockDto();
			sizeStockDto.setVariantId(supplementVariant.getProductSupplementVariantId());
			sizeStockDto.setStockAvailable(supplementVariant.getQuantity());
			sizeStockDto.setSize(supplementVariant.getSize() + " " + supplementVariant.getUnit());
			sizeStockDtos.add(sizeStockDto);
		}
		BeanUtils.copyProperties(information, productInformationDto);
		BeanUtils.copyProperties(productSupplement, productInformationDto);
		productInformationDto.setSize(productSupplement.getSize() + " " + productSupplement.getUnit());
		if (!productSupplement.getImages().isEmpty()) {
			productInformationDto.setProductImage(productSupplement.getImages().get(0));
		}
		productInformationDto.setVariantType("SUPPLEMENT");
		productInformationDto.setProductSizeStocks(sizeStockDtos);
		productInformationDto.setVariantId(productSupplement.getProductSupplementVariantId());
		return productInformationDto;
	}

	/**
	 * if cloth variant is not null then copying all variant data into
	 * ProductInformationDto
	 * 
	 * @param productCloth
	 * @param cartProduct
	 * @return
	 */
	private ProductInformationDto clothToProductDto(ProductClothVariant productCloth, CartProduct cartProduct) {
		List<ProductSizeStockDto> sizeStockDtos = new ArrayList<>();
		ProductInformation information = productCloth.getClothProduct();
		if (information.isDeleted()) {
			cartProductRepo.deleteById(cartProduct.getCartProductId());
			productInformationDto.setMessage(productInformationDto.getMessage() + " | " + information.getProductName());
		}
		List<ProductClothVariant> list = information.getClothVariants().stream()
				.filter(x -> x.getColor().equalsIgnoreCase(productCloth.getColor())).collect(Collectors.toList());
		for (ProductClothVariant clothVariant : list) {
			ProductSizeStockDto sizeStockDto = new ProductSizeStockDto();
			sizeStockDto.setVariantId(clothVariant.getClothVariantId());
			sizeStockDto.setStockAvailable(clothVariant.getQuantity());
			sizeStockDto.setSize(clothVariant.getSize());
			sizeStockDtos.add(sizeStockDto);
		}
		BeanUtils.copyProperties(information, productInformationDto);
		BeanUtils.copyProperties(productCloth, productInformationDto);
		if (!productCloth.getImages().isEmpty()) {
			productInformationDto.setProductImage(productCloth.getImages().get(0));
		}
		productInformationDto.setVariantType("CLOTH");
		productInformationDto.setProductSizeStocks(sizeStockDtos);
		productInformationDto.setVariantId(productCloth.getClothVariantId());
		return productInformationDto;
	}

	/**
	 * if accessory variant is not null then copying all variant data into
	 * ProductInformationDto
	 * 
	 * @param productAccessory
	 * @param cartProduct
	 * @return
	 */
	private ProductInformationDto accessoryToProductDto(ProductAccessoryVariant productAccessory,
			CartProduct cartProduct) {
		List<ProductSizeStockDto> sizeStockDtos = new ArrayList<>();
		ProductInformation information = productAccessory.getAccessoryProduct();
		if (information.isDeleted()) {
			cartProductRepo.deleteById(cartProduct.getCartProductId());
			productInformationDto.setMessage(productInformationDto.getMessage() + " | " + information.getProductName());
		}
		List<ProductAccessoryVariant> list = information.getAccessoryVariants().stream()
				.filter(x -> x.getColor().equalsIgnoreCase(productAccessory.getColor())).collect(Collectors.toList());
		for (ProductAccessoryVariant accessoryVariant : list) {
			ProductSizeStockDto sizeStockDto = new ProductSizeStockDto();
			sizeStockDto.setVariantId(accessoryVariant.getAccessoryVariantId());
			sizeStockDto.setStockAvailable(accessoryVariant.getQuantity());
			sizeStockDto.setSize(accessoryVariant.getSize() + " " + accessoryVariant.getUnit());
			sizeStockDtos.add(sizeStockDto);
		}
		BeanUtils.copyProperties(information, productInformationDto);
		BeanUtils.copyProperties(productAccessory, productInformationDto);
		productInformationDto.setSize(productAccessory.getSize() + " " + productAccessory.getUnit());
		if (!productAccessory.getImages().isEmpty()) {
			productInformationDto.setProductImage(productAccessory.getImages().get(0));
		}
		productInformationDto.setVariantType("ACCESSORY");
		productInformationDto.setProductSizeStocks(sizeStockDtos);
		productInformationDto.setVariantId(productAccessory.getAccessoryVariantId());
		return productInformationDto;
	}

	/**
	 * This method is implemented to delete Cart Products
	 * 
	 * @param userId
	 * @param cartProductId
	 */
	@Override
	@Transactional
	public boolean deleteCartProuduct(int userId, int cartProductId) {
		if (!userRepo.findById(userId).isPresent()) {
			throw new UserNotExistException();
		}
		CartProduct cartProduct = cartProductRepo.findById(cartProductId)
				.orElseThrow(() -> new ProductException("Cart Product Not Found"));
		if (cartProduct.isOrderd()) {
			throw new ProductException("Product Already been Placed");
		}
		cartProductRepo.deleteById(cartProductId);
		return false;
	}

}
