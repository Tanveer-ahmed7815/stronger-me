package com.tyss.strongameapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tyss.strongameapp.dto.AddsAndBannersDto;
import com.tyss.strongameapp.dto.AdvertisementInformationDto;
import com.tyss.strongameapp.dto.GetProductByVariantDTO;
import com.tyss.strongameapp.dto.MyShopDTO;
import com.tyss.strongameapp.dto.ProductInfoDTO;
import com.tyss.strongameapp.dto.ProductTypeDTO;
import com.tyss.strongameapp.dto.ShoppingBannerInformationDto;
import com.tyss.strongameapp.dto.VariantDTO;
import com.tyss.strongameapp.dto.VariantTypeDTO;
import com.tyss.strongameapp.entity.AdminRewardDetails;
import com.tyss.strongameapp.entity.AdvertisementInformation;
import com.tyss.strongameapp.entity.ProductAccessoryVariant;
import com.tyss.strongameapp.entity.ProductClothVariant;
import com.tyss.strongameapp.entity.ProductInformation;
import com.tyss.strongameapp.entity.ProductSupplementVariant;
import com.tyss.strongameapp.entity.RewardDetails;
import com.tyss.strongameapp.entity.ShoppingBannerInformation;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.exception.ProductException;
import com.tyss.strongameapp.repository.AdminRewardInformationRepository;
import com.tyss.strongameapp.repository.AdvertisementInformationRepository;
import com.tyss.strongameapp.repository.ProductAccessoryVariantRepo;
import com.tyss.strongameapp.repository.ProductClothVariantRepo;
import com.tyss.strongameapp.repository.ProductInformationRepository;
import com.tyss.strongameapp.repository.ProductSupplementVariantRepo;
import com.tyss.strongameapp.repository.RewardDetailsRepository;
import com.tyss.strongameapp.repository.ShoppingBannerInformationRepository;

/**
 * MyShopServiceImple is implementation class to display the content of my shop
 * page.
 * 
 * @author Sushma Guttal
 * @author Trupthi
 *
 */
@Service
public class MyShopServiceImple implements MyShopService {

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private ProductInformationRepository productRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private RewardDetailsRepository rewardRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private AdvertisementInformationRepository advertisementRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private AdminRewardInformationRepository adminRewardRepo;

	/**
	 * This field is used to invoke persistence layer methods.
	 */
	@Autowired
	private ShoppingBannerInformationRepository shopBannerRepo;

	@Autowired
	private ProductClothVariantRepo productClothVariantRepo;

	@Autowired
	private ProductSupplementVariantRepo productSupplementVariantRepo;

	@Autowired
	private ProductAccessoryVariantRepo productAccessoryVariantRepo;

	/**
	 * This field is used to invoke variables of application.properties.
	 */
	@Value("${email.username}")
	private String clientEmaidId;

	/**
	 * This field is used to invoke variables of application.properties.
	 */
	@Value("${email.password}")
	private String clientPassword;

	private static final String CLOTH_TYPE = "CLOTH";

	private static final String SUPPLEMENT_TYPE = "SUPPLEMENT";

	private static final String ACCESSORY_TYPE = "ACCESSORY";

	private static final String PRODUCT_TYPES = "TYPE can only be CLOTH, SUPPLEMENT or ACCESSORY";

	private static final String SORT_TYPES = "Type can only be ASC or DESC";

	/**
	 * This method is implemented to fetch coins of specified user.
	 * 
	 * @return double
	 */
	@Override
	public double getCoin(UserInformation user) {
		RewardDetails reward = user.getReward();
		double coins = rewardRepo.getCoin(reward.getRewardId());
		AdminRewardDetails adminReward = user.getAdminReward();
		double adminCoins = 0;
		if (adminReward != null) {
			adminCoins = adminRewardRepo.getAdminRewardCoin(adminReward.getAdminRewardId());
		}
		return coins + adminCoins;
	}// End of getCoin method.

	/**
	 * This method is implemented to fetch list of advertisements.
	 * 
	 * @return List<AdvertisementInformationDto>
	 */
	@Override
	public List<AdvertisementInformationDto> getAdvertisements() {
		List<AdvertisementInformation> advertisementList = advertisementRepo.findAll();
		if (advertisementList.isEmpty())
			return Collections.emptyList();
		else {
			List<AdvertisementInformationDto> advertisementDtoList = new ArrayList<>();
			for (AdvertisementInformation advertisementInformation : advertisementList) {
				AdvertisementInformationDto advertisementDto = new AdvertisementInformationDto();
				BeanUtils.copyProperties(advertisementInformation, advertisementDto);
				advertisementDtoList.add(advertisementDto);
			}
			return advertisementDtoList;
		}
	}// End of getAdvertisements method.

	/**
	 * This method is implemented to send mail to specified user.
	 * 
	 * @throws MessagingException
	 * @throws AddressException
	 */
	@Override
	public void sendMail(String email, String name, String type) throws MessagingException {
		final String username = clientEmaidId;
		final String password = clientPassword;
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtpout.secureserver.net");
		props.put("mail.smtp.port", "80");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Message message = new MimeMessage(session);
		Transport transport = session.getTransport();
		Address[] from = InternetAddress.parse(email);
		message.addFrom(from);
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("support@strongerme.in"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		if (type.equalsIgnoreCase("register")) {
			message.setSubject("Registration Successfull...!");
			message.setText("Dear" + " " + name + " " + "Welcome to Strong Me App");
		} else if (type.equalsIgnoreCase("order")) {
			message.setSubject("Order placed...!");
			message.setText("Dear" + " " + name + " " + "Your order has been placed");
		} else if (type.equalsIgnoreCase("forgotpassword")) {
			message.setSubject("Reset Password");
			message.setContent("<html>\n" + "<body>\n" + "\n" + "<a href=\"https://strongame.web.app/changepass/"+email+"\""+">\n"
					+ "This is a link for reset password</a>\n" + "\n" + "</body>\n" + "</html>", "text/html");

		}

		transport.connect("smtpout.secureserver.net", username, password);
		Transport.send(message);

	}// End of sendMail method.

	/**
	 * This method is implemented to fetch list of advertisements and shop banners.
	 * 
	 * @return List<AdvertisementInformationDto>
	 */
	@Override
	public AddsAndBannersDto getAddsBanners() {
		List<AdvertisementInformation> advertisementList = advertisementRepo.findAll();
		List<ShoppingBannerInformation> shopBannerList = shopBannerRepo.findAll();
		AddsAndBannersDto addsBannersDto = new AddsAndBannersDto();
		List<ShoppingBannerInformationDto> shopBannerDtoList = new ArrayList<>();
		for (ShoppingBannerInformation shoppingBannerInformation : shopBannerList) {
			ShoppingBannerInformationDto shopbannerDto = new ShoppingBannerInformationDto();
			BeanUtils.copyProperties(shoppingBannerInformation, shopbannerDto);
			if (shoppingBannerInformation.getShopbannerProduct() != null) {
				int id = shoppingBannerInformation.getShopbannerProduct().getProductId();
				shopbannerDto.setId(id);
			}
			shopBannerDtoList.add(shopbannerDto);
		}
		addsBannersDto.setAdvertisementList(advertisementList);
		addsBannersDto.setShopBannerList(shopBannerDtoList);

		return addsBannersDto;
	}

	@Override
	public List<MyShopDTO> getAllProducts(String type) {
		List<ProductInformation> products = productRepo.findByProductTypeAndIsDeleted(type, false);
		if (type.equalsIgnoreCase(CLOTH_TYPE)) {
			return getAllCloths(products);
		} else if (type.equalsIgnoreCase(SUPPLEMENT_TYPE)) {
			return getAllSupplements(products);
		} else if (type.equalsIgnoreCase(ACCESSORY_TYPE)) {
			return getAllAccessories(products);
		} else {
			throw new ProductException(PRODUCT_TYPES);
		}
	}

	private List<MyShopDTO> getAllCloths(List<ProductInformation> products) {
		List<MyShopDTO> myShopDTOs = new ArrayList<>();
		for (ProductInformation productInformation : products) {
			myShopDTOs.add(getCloth(productInformation));
		}
		return myShopDTOs;
	}

	private MyShopDTO getCloth(ProductInformation productInformation) {
		MyShopDTO myShopDTO = new MyShopDTO();
		BeanUtils.copyProperties(productInformation, myShopDTO);
		List<ProductClothVariant> productClothVariants = productInformation.getClothVariants();
		if (productClothVariants != null) {
			productClothVariants = productClothVariants.stream().filter(p -> !p.isDeleted())
					.collect(Collectors.toList());
			if (!productClothVariants.isEmpty()) {
				myShopDTO.setCoins(productClothVariants.get(0).getCoins());
				myShopDTO.setDiscount(productClothVariants.get(0).getDiscount());
				myShopDTO.setPrice(productClothVariants.get(0).getPrice());
				myShopDTO.setFinalPrice(getFinalPrice(myShopDTO.getPrice(), myShopDTO.getDiscount()));
			}
		}
		return myShopDTO;
	}

	private List<MyShopDTO> getAllSupplements(List<ProductInformation> products) {
		List<MyShopDTO> myShopDTOs = new ArrayList<>();
		for (ProductInformation productInformation : products) {
			myShopDTOs.add(getSupplement(productInformation));
		}
		return myShopDTOs;
	}

	private MyShopDTO getSupplement(ProductInformation productInformation) {
		MyShopDTO myShopDTO = new MyShopDTO();
		BeanUtils.copyProperties(productInformation, myShopDTO);
		List<ProductSupplementVariant> productSupplementVariants = productInformation.getProductSupplementVariant();
		if (productSupplementVariants != null) {
			productSupplementVariants = productSupplementVariants.stream().filter(p -> !p.isDeleted())
					.collect(Collectors.toList());
			if (!productSupplementVariants.isEmpty()) {
				myShopDTO.setCoins(productSupplementVariants.get(0).getCoins());
				myShopDTO.setDiscount(productSupplementVariants.get(0).getDiscount());
				myShopDTO.setPrice(productSupplementVariants.get(0).getPrice());
				myShopDTO.setFinalPrice(getFinalPrice(myShopDTO.getPrice(), myShopDTO.getDiscount()));
			}
		}
		return myShopDTO;
	}

	private List<MyShopDTO> getAllAccessories(List<ProductInformation> products) {
		List<MyShopDTO> myShopDTOs = new ArrayList<>();
		for (ProductInformation productInformation : products) {
			myShopDTOs.add(getAccessory(productInformation));
		}
		return myShopDTOs;
	}

	private MyShopDTO getAccessory(ProductInformation productInformation) {
		MyShopDTO myShopDTO = new MyShopDTO();
		BeanUtils.copyProperties(productInformation, myShopDTO);
		List<ProductAccessoryVariant> productAccessoryVariants = productInformation.getAccessoryVariants();
		if (productAccessoryVariants != null) {
			productAccessoryVariants = productAccessoryVariants.stream().filter(p -> !p.isDeleted())
					.collect(Collectors.toList());
			if (!productAccessoryVariants.isEmpty()) {
				myShopDTO.setCoins(productAccessoryVariants.get(0).getCoins());
				myShopDTO.setDiscount(productAccessoryVariants.get(0).getDiscount());
				myShopDTO.setPrice(productAccessoryVariants.get(0).getPrice());
				myShopDTO.setFinalPrice(getFinalPrice(myShopDTO.getPrice(), myShopDTO.getDiscount()));
			}
		}
		return myShopDTO;
	}

	@Override
	public ProductInfoDTO getProductById(int productId) {
		String selectedVariant = null;
		return getProductDetails(productId, selectedVariant);
	}

	@Override
	public ProductInfoDTO getProductByVariant(GetProductByVariantDTO getProductByVariantDTO) {
		if (getProductByVariantDTO != null) {
			return getProductDetails(getProductByVariantDTO.getProductId(), getProductByVariantDTO.getFirstVariant());
		} else {
			throw new ProductException("Data can not be null");
		}
	}

	public ProductInfoDTO getProductDetails(int productId, String selectedVariant) {
		ProductInformation productInformation = productRepo.findByProductIdAndIsDeleted(productId, false);
		if (productInformation != null) {
			ProductInfoDTO productInfoDTO = new ProductInfoDTO();
			BeanUtils.copyProperties(productInformation, productInfoDTO);
			if (productInformation.getProductType().equalsIgnoreCase(CLOTH_TYPE)) {
				return getClothVaraints(productInfoDTO, productInformation, selectedVariant);
			} else if (productInformation.getProductType().equalsIgnoreCase(SUPPLEMENT_TYPE)) {
				return getSupplementVaraints(productInfoDTO, productInformation, selectedVariant);
			} else if (productInformation.getProductType().equalsIgnoreCase(ACCESSORY_TYPE)) {
				return getAccessoryVaraints(productInfoDTO, productInformation, selectedVariant);
			} else {
				throw new ProductException(PRODUCT_TYPES);
			}
		} else {
			return null;
		}
	}

	private ProductInfoDTO getClothVaraints(ProductInfoDTO productInfoDTO, ProductInformation productInformation,
			String selectedVariant) {
		List<ProductClothVariant> productClothVariants = productInformation.getClothVariants();
		if (productClothVariants != null) {
			List<String> colors = productClothVariants.stream().filter(c -> (!c.isDeleted()))
					.map(ProductClothVariant::getColor).distinct().collect(Collectors.toList());
			productInfoDTO.setFirstVariant(colors);
			if (selectedVariant == null) {
				if (!colors.isEmpty()) {
					productInfoDTO.setSecondVariant(getSizeForCloths(colors.get(0), productInformation));
				}
			} else {
				productInfoDTO.setSecondVariant(getSizeForCloths(selectedVariant, productInformation));
			}
		}
		return productInfoDTO;
	}

	private List<VariantDTO> getSizeForCloths(String color, ProductInformation productInformation) {
		List<VariantDTO> variantDTOs = new ArrayList<>();
		List<ProductClothVariant> productClothVariants = productClothVariantRepo
				.findByClothProductAndColorAndIsDeleted(productInformation, color, false);
		for (ProductClothVariant productClothVariant : productClothVariants) {
			VariantDTO variantDTO = new VariantDTO();
			BeanUtils.copyProperties(productClothVariant, variantDTO);
			variantDTO.setId(productClothVariant.getClothVariantId());
			variantDTO.setFinalPrice(getFinalPrice(variantDTO.getPrice(), variantDTO.getDiscount()));
			variantDTOs.add(variantDTO);
		}
		return variantDTOs;
	}

	private ProductInfoDTO getSupplementVaraints(ProductInfoDTO productInfoDTO, ProductInformation productInformation,
			String selectedVariant) {
		List<ProductSupplementVariant> productSupplementVariants = productInformation.getProductSupplementVariant();
		if (productSupplementVariants != null) {
			List<String> flavours = productSupplementVariants.stream().filter(s -> (!s.isDeleted()))
					.map(ProductSupplementVariant::getFlavour).distinct().collect(Collectors.toList());
			productInfoDTO.setFirstVariant(flavours);
			if (selectedVariant == null) {
				if (!flavours.isEmpty()) {
					productInfoDTO.setSecondVariant(getSizeForSupplements(flavours.get(0), productInformation));
				}
			} else {
				productInfoDTO.setSecondVariant(getSizeForSupplements(selectedVariant, productInformation));
			}
		}
		return productInfoDTO;
	}

	private List<VariantDTO> getSizeForSupplements(String flavour, ProductInformation productInformation) {
		List<VariantDTO> variantDTOs = new ArrayList<>();
		List<ProductSupplementVariant> productSupplementVariants = productSupplementVariantRepo
				.findBySupplementProductAndFlavourAndIsDeleted(productInformation, flavour, false);
		for (ProductSupplementVariant productSupplementVariant : productSupplementVariants) {
			VariantDTO variantDTO = new VariantDTO();
			BeanUtils.copyProperties(productSupplementVariant, variantDTO);
			variantDTO.setFinalPrice(getFinalPrice(variantDTO.getPrice(), variantDTO.getDiscount()));
			variantDTO.setId(productSupplementVariant.getProductSupplementVariantId());
			variantDTO.setSizeWithUnit(productSupplementVariant.getSize() + " " + productSupplementVariant.getUnit());
			variantDTOs.add(variantDTO);
		}
		return variantDTOs;
	}

	private ProductInfoDTO getAccessoryVaraints(ProductInfoDTO productInfoDTO, ProductInformation productInformation,
			String selectedVariant) {
		List<ProductAccessoryVariant> productAccessoryVariant = productInformation.getAccessoryVariants();
		if (productAccessoryVariant != null) {
			List<String> colors = productAccessoryVariant.stream()
					.filter(a -> (a.getColor() != null) && (!a.isDeleted())).map(ProductAccessoryVariant::getColor)
					.distinct().collect(Collectors.toList());
			productInfoDTO.setFirstVariant(colors);
			if (selectedVariant == null) {
				if (!colors.isEmpty()) {
					productInfoDTO.setSecondVariant(getSizeForAccessories(colors.get(0), productInformation));
				} else {
					productInfoDTO.setSecondVariant(getSizeForAccessories(null, productInformation));
				}
			} else {
				productInfoDTO.setSecondVariant(getSizeForAccessories(selectedVariant, productInformation));
			}
		}
		return productInfoDTO;
	}

	private List<VariantDTO> getSizeForAccessories(String color, ProductInformation productInformation) {
		List<VariantDTO> variantDTOs = new ArrayList<>();
		List<ProductAccessoryVariant> productAccessoryVariants = productAccessoryVariantRepo
				.findByAccessoryProductAndColorAndIsDeleted(productInformation, color, false);
		for (ProductAccessoryVariant productAccessoryVariant : productAccessoryVariants) {

			VariantDTO variantDTO = new VariantDTO();
			BeanUtils.copyProperties(productAccessoryVariant, variantDTO);
			variantDTO.setFinalPrice(getFinalPrice(variantDTO.getPrice(), variantDTO.getDiscount()));
			if (productAccessoryVariant.getSize() != 0 && productAccessoryVariant.getUnit() != null) {
				variantDTO.setSizeWithUnit(productAccessoryVariant.getSize() + " " + productAccessoryVariant.getUnit());
			}
			variantDTO.setId(productAccessoryVariant.getAccessoryVariantId());
			variantDTOs.add(variantDTO);
		}
		return variantDTOs;
	}

	/**
	 * This method is implemented to filter the products by coins in ascending
	 * order.
	 * 
	 * @return List<ProductInformationDto>
	 */
	@Override
	public List<MyShopDTO> sortProductsByPrice(String productType, String type) {
		List<MyShopDTO> myShopDTOs = getAllProducts(productType);
		if (!myShopDTOs.isEmpty()) {
			if ("ASC".equalsIgnoreCase(type)) {
				return myShopDTOs.stream().sorted((o1, o2) -> Double.compare(o1.getFinalPrice(), o2.getFinalPrice()))
						.collect(Collectors.toList());
			} else if ("DESC".equalsIgnoreCase(type)) {
				return myShopDTOs.stream().sorted((o1, o2) -> Double.compare(o2.getFinalPrice(), o1.getFinalPrice()))
						.collect(Collectors.toList());
			} else {
				throw new ProductException(SORT_TYPES);
			}

		} else
			return Collections.emptyList();

	}// End of filterProductByCoin method.

	/**
	 * This method is implemented to filter the products by coins in ascending
	 * order.
	 * 
	 * @return List<ProductInformationDto>
	 */
	@Override
	public List<MyShopDTO> sortProductsByCoin(String productType, String type) {
		List<MyShopDTO> myShopDTOs = getAllProducts(productType);
		if (!myShopDTOs.isEmpty()) {
			if ("ASC".equalsIgnoreCase(type)) {
				return myShopDTOs.stream().sorted((o1, o2) -> Double.compare(o1.getCoins(), o2.getCoins()))
						.collect(Collectors.toList());
			} else if ("DESC".equalsIgnoreCase(type)) {
				return myShopDTOs.stream().sorted((o1, o2) -> Double.compare(o2.getCoins(), o1.getCoins()))
						.collect(Collectors.toList());
			} else {
				throw new ProductException(SORT_TYPES);
			}

		} else
			return Collections.emptyList();

	}// End of filterProductByCoin method.

	/**
	 * This method is implemented to filter the products by coins in ascending
	 * order.
	 * 
	 * @return List<ProductInformationDto>
	 */
	@Override
	public List<MyShopDTO> sortProductsByName(String productType, String type) {
		List<MyShopDTO> myShopDTOs = getAllProducts(productType);
		if (!myShopDTOs.isEmpty()) {
			if ("ASC".equalsIgnoreCase(type)) {
				return myShopDTOs.stream()
						.sorted((o1, o2) -> o1.getProductName().compareToIgnoreCase(o2.getProductName()))
						.collect(Collectors.toList());
			} else if ("DESC".equalsIgnoreCase(type)) {
				return myShopDTOs.stream()
						.sorted((o1, o2) -> o2.getProductName().compareToIgnoreCase(o1.getProductName()))
						.collect(Collectors.toList());
			} else {
				throw new ProductException(SORT_TYPES);
			}

		} else
			return Collections.emptyList();

	}// End of filterProductByCoin method.

	private double getFinalPrice(double price, double discount) {
		double savings = (discount * price) / 100;
		return price - savings;
	}

	@Override
	public VariantTypeDTO getProductFilters(String productType) {
		if (productType.equalsIgnoreCase(CLOTH_TYPE)) {
			return getClothFilters();
		} else if (productType.equalsIgnoreCase(SUPPLEMENT_TYPE)) {
			return getSupplementFilters();
		} else if (productType.equalsIgnoreCase(ACCESSORY_TYPE)) {
			return getAccessoryFilters();
		} else {
			throw new ProductException(PRODUCT_TYPES);
		}

	}

	private VariantTypeDTO getClothFilters() {
		List<ProductClothVariant> productClothVariants = productClothVariantRepo.findAll();
		productClothVariants = productClothVariants.stream().filter(p -> !p.isDeleted()).collect(Collectors.toList());
		List<String> colors = productClothVariants.stream().map(ProductClothVariant::getColor).distinct()
				.collect(Collectors.toList());
		List<String> sizes = productClothVariants.stream().map(ProductClothVariant::getSize).distinct()
				.collect(Collectors.toList());
		if (colors.isEmpty() && sizes.isEmpty()) {
			return null;
		} else {
			return new VariantTypeDTO(colors, sizes);
		}
	}

	private VariantTypeDTO getSupplementFilters() {
		List<ProductSupplementVariant> productSupplementVariants = productSupplementVariantRepo.findAll();
		productSupplementVariants = productSupplementVariants.stream().filter(p -> !p.isDeleted())
				.collect(Collectors.toList());
		List<String> flavours = productSupplementVariants.stream().map(ProductSupplementVariant::getFlavour).distinct()
				.collect(Collectors.toList());
		List<String> sizes = productSupplementVariants.stream().map(p -> p.getSize() + " " + p.getUnit()).distinct()
				.collect(Collectors.toList());
		if (flavours.isEmpty() && sizes.isEmpty()) {
			return null;
		} else {
			return new VariantTypeDTO(flavours, sizes);
		}
	}

	private VariantTypeDTO getAccessoryFilters() {
		List<ProductAccessoryVariant> productAccessoryVariants = productAccessoryVariantRepo.findAll();
		productAccessoryVariants = productAccessoryVariants.stream().filter(p -> !p.isDeleted())
				.collect(Collectors.toList());
		List<String> colors = productAccessoryVariants.stream().map(ProductAccessoryVariant::getColor).distinct()
				.collect(Collectors.toList());
		List<String> sizes = productAccessoryVariants.stream().map(p -> p.getSize() + " " + p.getUnit()).distinct()
				.collect(Collectors.toList());
		if (colors.isEmpty() && sizes.isEmpty()) {
			return null;
		} else {
			return new VariantTypeDTO(colors, sizes);
		}
	}

	@Override
	public List<MyShopDTO> getFilteredProducts(String productType, VariantTypeDTO variantTypeDTO) {
		List<ProductInformation> products = productRepo.findByProductTypeAndIsDeleted(productType, false);
		if (productType.equalsIgnoreCase(CLOTH_TYPE)) {
			return getFilteredCloths(variantTypeDTO, products);
		} else if (productType.equalsIgnoreCase(SUPPLEMENT_TYPE)) {
			return getFilteredSupplements(variantTypeDTO, products);
		} else if (productType.equalsIgnoreCase(ACCESSORY_TYPE)) {
			return getFilteredAccessories(variantTypeDTO, products);
		} else {
			throw new ProductException(PRODUCT_TYPES);
		}

	}

	List<MyShopDTO> getFilteredCloths(VariantTypeDTO variantTypeDTO, List<ProductInformation> products) {
		List<MyShopDTO> myShopDTOs = new ArrayList<>();
		for (ProductInformation productInformation : products) {
			List<ProductClothVariant> productClothVariants = productInformation.getClothVariants();
			if (productClothVariants != null) {
				productClothVariants = getFilteredClothsHelper(productClothVariants, variantTypeDTO);
				if (!productClothVariants.isEmpty()) {
					myShopDTOs.add(getCloth(productInformation));
				}
			}
		}
		return myShopDTOs;
	}

	private List<ProductClothVariant> getFilteredClothsHelper(List<ProductClothVariant> productClothVariants,
			VariantTypeDTO variantTypeDTO) {
		if (variantTypeDTO.getFirstVariant() != null && !(variantTypeDTO.getFirstVariant().isEmpty())) {
			productClothVariants = productClothVariants.stream()
					.filter(c -> (!c.isDeleted()) && (variantTypeDTO.getFirstVariant().equalsIgnoreCase(c.getColor())))
					.collect(Collectors.toList());
		}
		if (variantTypeDTO.getSecondVariant() != null && !(variantTypeDTO.getSecondVariant().isEmpty())) {
			productClothVariants = productClothVariants.stream()
					.filter(c -> (!c.isDeleted()) && (variantTypeDTO.getSecondVariant().equalsIgnoreCase(c.getSize())))
					.collect(Collectors.toList());
		}
		return productClothVariants;
	}

	private List<MyShopDTO> getFilteredSupplements(VariantTypeDTO variantTypeDTO, List<ProductInformation> products) {
		List<MyShopDTO> myShopDTOs = new ArrayList<>();
		for (ProductInformation productInformation : products) {
			List<ProductSupplementVariant> productSupplementVariants = productInformation.getProductSupplementVariant();
			if (productSupplementVariants != null) {
				productSupplementVariants = getFilteredSupplementHelper(productSupplementVariants, variantTypeDTO);
				if (!productSupplementVariants.isEmpty()) {
					myShopDTOs.add(getSupplement(productInformation));
				}
			}
		}
		return myShopDTOs;
	}

	private List<ProductSupplementVariant> getFilteredSupplementHelper(
			List<ProductSupplementVariant> productSupplementVariants, VariantTypeDTO variantTypeDTO) {
		if (variantTypeDTO.getFirstVariant() != null && !(variantTypeDTO.getFirstVariant().isEmpty())) {
			productSupplementVariants = productSupplementVariants.stream().filter(
					s -> (!s.isDeleted()) && (variantTypeDTO.getFirstVariant().equalsIgnoreCase(s.getFlavour())))
					.collect(Collectors.toList());
		}
		if (variantTypeDTO.getSecondVariant() != null && !(variantTypeDTO.getSecondVariant().isEmpty())) {
			String[] sizeWithUit = variantTypeDTO.getSecondVariant().split(" ", 1);
			double size = Double.parseDouble(sizeWithUit[0]);
			productSupplementVariants = productSupplementVariants.stream().filter(
					s -> (!s.isDeleted()) && (size == s.getSize()) && (sizeWithUit[1].equalsIgnoreCase(s.getUnit())))
					.collect(Collectors.toList());
		}
		return productSupplementVariants;
	}

	private List<MyShopDTO> getFilteredAccessories(VariantTypeDTO variantTypeDTO, List<ProductInformation> products) {
		List<MyShopDTO> myShopDTOs = new ArrayList<>();
		for (ProductInformation productInformation : products) {
			List<ProductAccessoryVariant> productAccessoryVariants = productInformation.getAccessoryVariants();
			if (productAccessoryVariants != null) {
				productAccessoryVariants = getFilteredAccessoriesHelper(productAccessoryVariants, variantTypeDTO);
				if (!productAccessoryVariants.isEmpty()) {
					myShopDTOs.add(getAccessory(productInformation));
				}
			}
		}
		return myShopDTOs;
	}

	private List<ProductAccessoryVariant> getFilteredAccessoriesHelper(
			List<ProductAccessoryVariant> productAccessoryVariants, VariantTypeDTO variantTypeDTO) {
		if (variantTypeDTO.getFirstVariant() != null && !(variantTypeDTO.getFirstVariant().isEmpty())) {
			productAccessoryVariants = productAccessoryVariants.stream()
					.filter(s -> (!s.isDeleted()) && (variantTypeDTO.getFirstVariant().equalsIgnoreCase(s.getColor())))
					.collect(Collectors.toList());
		}
		if (variantTypeDTO.getSecondVariant() != null && !(variantTypeDTO.getSecondVariant().isEmpty())) {
			String[] sizeWithUit = variantTypeDTO.getSecondVariant().split(" ", 1);
			double size = Double.parseDouble(sizeWithUit[0]);
			productAccessoryVariants = productAccessoryVariants.stream().filter(
					s -> (!s.isDeleted()) && (size == s.getSize()) && (sizeWithUit[1].equalsIgnoreCase(s.getUnit())))
					.collect(Collectors.toList());
		}
		return productAccessoryVariants;
	}

	@Override
	public List<ProductTypeDTO> getProductTypes() {
		List<ProductTypeDTO> productTypes = new ArrayList<>();
		List<ProductInformation> products = productRepo.findAll();

		List<ProductInformation> cloths = products.stream()
				.filter(p -> (!p.isDeleted()) && (CLOTH_TYPE.equalsIgnoreCase(p.getProductType())))
				.collect(Collectors.toList());
		if (!cloths.isEmpty()) {
			productTypes.add(new ProductTypeDTO(CLOTH_TYPE, cloths.get(cloths.size() - 1).getProductImage()));
		}

		List<ProductInformation> supplements = products.stream()
				.filter(p -> (!p.isDeleted()) && (SUPPLEMENT_TYPE.equalsIgnoreCase(p.getProductType())))
				.collect(Collectors.toList());
		if (!supplements.isEmpty()) {
			productTypes.add(
					new ProductTypeDTO(SUPPLEMENT_TYPE, supplements.get(supplements.size() - 1).getProductImage()));
		}

		List<ProductInformation> accessories = products.stream()
				.filter(p -> (!p.isDeleted()) && (ACCESSORY_TYPE.equalsIgnoreCase(p.getProductType())))
				.collect(Collectors.toList());
		if (!accessories.isEmpty()) {
			productTypes
					.add(new ProductTypeDTO(ACCESSORY_TYPE, accessories.get(accessories.size() - 1).getProductImage()));
		}

		return productTypes;
	}

}// End of MyShopServiceImple class
