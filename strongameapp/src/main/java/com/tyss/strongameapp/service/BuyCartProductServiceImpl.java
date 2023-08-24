
package com.tyss.strongameapp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.tyss.strongameapp.dto.PlaceOrderDto;
import com.tyss.strongameapp.entity.AdminRewardDetails;
import com.tyss.strongameapp.entity.CartProduct;
import com.tyss.strongameapp.entity.MyOrderDetails;
import com.tyss.strongameapp.entity.NotificationInformation;
import com.tyss.strongameapp.entity.OrderInformation;
import com.tyss.strongameapp.entity.ProductAccessoryVariant;
import com.tyss.strongameapp.entity.ProductClothVariant;
import com.tyss.strongameapp.entity.ProductInformation;
import com.tyss.strongameapp.entity.ProductSupplementVariant;
import com.tyss.strongameapp.entity.RewardDetails;
import com.tyss.strongameapp.entity.UserInformation;
import com.tyss.strongameapp.exception.AddressTypeException;
import com.tyss.strongameapp.exception.ProductException;
import com.tyss.strongameapp.exception.UserNotExistException;
import com.tyss.strongameapp.repository.NotificationInformationRepository;
import com.tyss.strongameapp.repository.UserInformationRepository;
import com.tyss.strongameapp.util.MyFireBaseUtility;

/**
 * This is the implementation class to place/order the cart products.
 * 
 * @author Afridi
 *
 */
@Service
public class BuyCartProductServiceImpl implements BuyCartProductService {

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private UserInformationRepository userRepo;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private MyShopService myShopService;

	/**
	 * This field is to invoke persistence layer method.
	 */
	@Autowired
	private NotificationInformationRepository notificationRepo;

	/**
	 * This field is to invoke firebaseService method.
	 */
	@Autowired
	private MyFireBaseUtility firebaseService;

	private double price;

	/**
	 * This method is used for placing the order.
	 * 
	 * @param cartPlaceDto
	 * @return String
	 */
	@Override
	@Transactional
	public String placeAnOrder(PlaceOrderDto placeOrderDto) throws FirebaseMessagingException {
		String response = "Cart is Empty";
		UserInformation fetchedUser = userRepo.findById(placeOrderDto.getUserId())
				.orElseThrow(UserNotExistException::new);
		List<CartProduct> cartProducts = fetchedUser.getCartProducts();
		OrderInformation information = new OrderInformation();
		ProductInformation productDetails;
		if (!cartProducts.isEmpty()) {
			int countProduct = 0;
			for (CartProduct addedcartProduct : cartProducts) {
				productDetails = getproductInfo(addedcartProduct);
				fetchedUser.getProduct().add(productDetails);
				productDetails.getOrder().add(information);
				countProduct++;
			}
			// filling order information
			information.setOrderDate(placeOrderDto.getOrderDate());
			information.setSavedAddress(fetchedUser.getSavedAddress().stream()
					.filter(x -> x.getSavedAddressId() == placeOrderDto.getAddressId()).findFirst()
					.orElseThrow(() -> new AddressTypeException("Address Not Found")));
			List<MyOrderDetails> myOrders = new ArrayList<>();

			for (CartProduct cartProduct : cartProducts) {
				myOrders.add(addMyOrderDetails(cartProduct, information));
				updateStockAndBalance(cartProduct, fetchedUser);
				fetchedUser.getNotificaton().add(addNotification(getproductInfo(cartProduct), fetchedUser.getName()));
				cartProduct.setOrderd(true);
			}
			information.setMyorder(myOrders);
			fetchedUser.getMyorder().addAll(myOrders);
			information.setOrderUser(fetchedUser);
			double totalUsedCoins = cartProducts.stream().map(CartProduct::getUsedCoins).reduce(0.0, Double::sum);
			response = " Order Placed Successfully and Balance Coins : "
					+ (myShopService.getCoin(fetchedUser) - totalUsedCoins);
			// myShopService.sendMail(fetchedUser.getEmail(), fetchedUser.getName(),
			// "order");
			ProductInformation productInformation = getproductInfo(cartProducts.get(0));
			response = countProduct > 1
					? productInformation.getProductName() + " and " + (countProduct - 1) + " More Have Been" + response
					: productInformation.getProductName() + response;

			firebaseService.sendTokenNotification(fetchedUser.getFirebaseToken(), response,
					productInformation.getProductImage());
			fetchedUser.setCartProducts(Collections.emptyList());
			userRepo.save(fetchedUser);
		}
		return response;
	}

	@Transactional
	private NotificationInformation addNotification(ProductInformation productInformation, String userName) {
		NotificationInformation notification = new NotificationInformation();
		notification.setNotificationDetails(
				"Your Order Has Been Placed For Product," + " " + productInformation.getProductName());
		notification.setNotificationType("specific");
		notification.setNotificationImage(productInformation.getProductImage());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 330);
		SimpleDateFormat sdfNow = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String s1 = sdfNow.format(calendar.getTime());
		NotificationInformation notificationTwo = new NotificationInformation();
		notificationTwo.setNotificationDetails(
				userName + " " + "Has Ordered" + " " + productInformation.getProductName() + " " + "On" + " " + s1);
		notificationTwo.setNotificationImage(productInformation.getProductImage());
		notificationTwo.setNotificationType("product");
		notificationRepo.save(notificationTwo);
		return notification;
	}

	@Transactional
	private UserInformation updateStockAndBalance(CartProduct cartProduct, UserInformation fetchedUser) {
		ProductAccessoryVariant productAccessory = cartProduct.getProductAccessory();
		ProductClothVariant clothVariant = cartProduct.getProductCloth();
		ProductSupplementVariant productSupplement = cartProduct.getProductSupplement();
		if (productAccessory != null) {
			productAccessory.setQuantity(productAccessory.getQuantity() - cartProduct.getQuantity());
			price = productAccessory.getPrice();
		} else if (clothVariant != null) {
			clothVariant.setQuantity(clothVariant.getQuantity() - cartProduct.getQuantity());
			price = clothVariant.getPrice();
		} else if (productSupplement != null) {
			productSupplement.setQuantity(productSupplement.getQuantity() - cartProduct.getQuantity());
			price = productSupplement.getPrice();
		}

		double usedCoins = cartProduct.getUsedCoins();
		RewardDetails rewardDetails = fetchedUser.getReward();
		AdminRewardDetails adminRewardDetails = fetchedUser.getAdminReward();
		double rewardCoins = rewardDetails.getRewardCoins();
		if (rewardCoins >= usedCoins) {
			double balanceCoin = rewardCoins - usedCoins;
			double balanceCoin2 = Double.parseDouble(String.format("%.2f", balanceCoin));
			rewardDetails.setRewardCoins(balanceCoin2);
		} else {
			rewardDetails.setRewardCoins(0);
			double balanceCoin = usedCoins - rewardCoins;
			double adminCoins = adminRewardDetails.getAdminRewardCoins();
			double adminBalanceCoin = adminCoins - balanceCoin;
			double adminBalanceCoin2 = Double.parseDouble(String.format("%.2f", adminBalanceCoin));
			adminRewardDetails.setAdminRewardCoins(adminBalanceCoin2);
			fetchedUser.setAdminReward(adminRewardDetails);
		}
		fetchedUser.setReward(rewardDetails);
		return fetchedUser;
	}

	@Transactional
	private MyOrderDetails addMyOrderDetails(CartProduct cartProduct, OrderInformation orderInformation) {
		long ltime = new Date().getTime() + 5 * 24 * 60 * 60 * 1000;
		Date updatedDate = new Date(ltime);
		MyOrderDetails myOrder = new MyOrderDetails();
		BeanUtils.copyProperties(cartProduct, myOrder);
		myOrder.setType("product");
		myOrder.setOrderStatus("In - Process");
		myOrder.setDeliveryDate(updatedDate);
		myOrder.setUserMyOrder(cartProduct.getCartProudutUser());
		myOrder.setPaidProductPrice(cartProduct.getToBePaid());
		ProductInformation getproductInfo = getproductInfo(cartProduct);
		myOrder.setName(getproductInfo.getProductName());
		myOrder.setImage(getproductInfo.getProductImage());
		myOrder.setPrice(price);
		myOrder.setOrderInformation(orderInformation);
		myOrder.setCartProduct(cartProduct);
		return myOrder;
	}

	private ProductInformation getproductInfo(CartProduct cartProduct) {
		ProductAccessoryVariant productAccessory = cartProduct.getProductAccessory();
		ProductClothVariant productCloth = cartProduct.getProductCloth();
		ProductSupplementVariant productSupplement = cartProduct.getProductSupplement();
		if (productAccessory != null) {
			return productAccessory.getAccessoryProduct();
		} else if (productCloth != null) {
			return productCloth.getClothProduct();
		} else if (productSupplement != null) {
			return productSupplement.getSupplementProduct();
		} else {
			throw new ProductException("Product Variant Not Found");
		}
	}

} // End of BuyProductServiceImple class.
