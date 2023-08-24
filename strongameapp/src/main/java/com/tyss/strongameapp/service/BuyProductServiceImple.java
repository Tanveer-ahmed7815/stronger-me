/*
 * package com.tyss.strongameapp.service;
 * 
 * import java.text.SimpleDateFormat; import java.util.ArrayList; import
 * java.util.Calendar; import java.util.Collections; import java.util.Date;
 * import java.util.List; import java.util.Optional;
 * 
 * import javax.transaction.Transactional;
 * 
 * import org.springframework.beans.BeanUtils; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Service;
 * 
 * import com.google.firebase.messaging.FirebaseMessagingException; import
 * com.tyss.strongameapp.dto.OrderCartPlaceDto; import
 * com.tyss.strongameapp.dto.OrderInformationDto; import
 * com.tyss.strongameapp.dto.ProductInformationDto; import
 * com.tyss.strongameapp.entity.CartProduct; import
 * com.tyss.strongameapp.entity.MyOrderDetails; import
 * com.tyss.strongameapp.entity.NotificationInformation; import
 * com.tyss.strongameapp.entity.OrderInformation; import
 * com.tyss.strongameapp.entity.ProductInformation; import
 * com.tyss.strongameapp.entity.ProductSizeStock; import
 * com.tyss.strongameapp.entity.UserInformation; import
 * com.tyss.strongameapp.exception.UserExist; import
 * com.tyss.strongameapp.repository.AdminRewardInformationRepository; import
 * com.tyss.strongameapp.repository.CartProductRepo; import
 * com.tyss.strongameapp.repository.MyOrderDetailsRepository; import
 * com.tyss.strongameapp.repository.NotificationInformationRepository; import
 * com.tyss.strongameapp.repository.OrderInformationRepository; import
 * com.tyss.strongameapp.repository.ProductInformationRepository; import
 * com.tyss.strongameapp.repository.RewardDetailsRepository; import
 * com.tyss.strongameapp.repository.UserInformationRepository;
 * 
 *//**
	 * This is the implementation class to place/order the product.
	 * 
	 * @author Sushma Guttal
	 *
	 */
/*
 * 
 * @Service public class BuyProductServiceImple implements BuyProductService {
 * 
 *//**
	 * This field is to invoke persistence layer method.
	 */
/*
 * @Autowired private OrderInformationRepository orderRepo;
 * 
 *//**
	 * This field is to invoke persistence layer method.
	 */
/*
 * @Autowired private UserInformationRepository userRepo;
 * 
 *//**
	 * This field is to invoke persistence layer method.
	 */
/*
 * @Autowired private RewardDetailsRepository rewardRepo;
 * 
 *//**
	 * This field is to invoke persistence layer method.
	 */
/*
 * @Autowired private MyShopService myShopService;
 * 
 *//**
	 * This field is to invoke persistence layer method.
	 */
/*
 * @Autowired private AdminRewardInformationRepository adminRewardRepo;
 * 
 *//**
	 * This field is to invoke persistence layer method.
	 */
/*
 * @Autowired private NotificationInformationRepository notificationRepo;
 * 
 * @Autowired private MyOrderDetailsRepository myOrderRepository;
 * 
 * @Autowired private MyFireBaseService firebaseService;
 * 
 * @Autowired private ProductInformationRepository productRepo;
 * 
 * @Autowired private CartProductRepo cartProductRepo;
 * 
 *//**
	 * This method is used for placing the order.
	 * 
	 * @param order, product, user
	 * @return String
	 *//*
		 * @Override public String placeOrder(OrderInformationDto order,
		 * Optional<ProductInformation> productEntity, Optional<UserInformation>
		 * userEntity) { String response = ""; if (!userEntity.isPresent()) { response =
		 * response.concat("User Not Found."); } if (order == null) { response =
		 * response.concat("Order Not Found."); } if (!productEntity.isPresent()) {
		 * response = response.concat("Product Not Found."); } if (response.length() ==
		 * 0) { ProductInformation product = productEntity.get(); UserInformation user =
		 * userEntity.get(); double userCoins = myShopService.getCoin(user); double
		 * productCoins = product.getActualCoins(); OrderInformation orderEntity = new
		 * OrderInformation(); BeanUtils.copyProperties(order, orderEntity); double
		 * discount = product.getDiscount(); double savings = (discount * productCoins)
		 * / 100; double saleCoins = productCoins - savings; if (userCoins >= saleCoins)
		 * { user.getProduct().add(product); product.getOrder().add(orderEntity);
		 * orderEntity.setOrderUser(user); orderRepo.save(orderEntity); double
		 * rewardCoins = user.getReward().getRewardCoins(); if (rewardCoins >=
		 * saleCoins) { double balanceCoin = rewardCoins - saleCoins; double
		 * balanceCoin2 = Double.parseDouble(String.format("%.2f", balanceCoin));
		 * rewardRepo.updateReward(balanceCoin2, user.getReward().getRewardId()); } else
		 * { rewardRepo.updateRewardToZero(user.getReward().getRewardId()); double
		 * balanceCoin = saleCoins - rewardCoins; double adminCoins =
		 * user.getAdminReward().getAdminRewardCoins(); double adminBalanceCoin =
		 * adminCoins - balanceCoin; double adminBalanceCoin2 =
		 * Double.parseDouble(String.format("%.2f", adminBalanceCoin));
		 * adminRewardRepo.updateAdminReward(adminBalanceCoin2,
		 * user.getAdminReward().getAdminRewardId()); } double remainingCoins =
		 * userCoins - saleCoins; response =
		 * "Order placed successfully and balance coins are" + " " +
		 * Double.parseDouble(String.format("%.2f", remainingCoins));
		 * myShopService.sendMail(user.getEmail(), user.getName(), "order");
		 * NotificationInformation notification = new NotificationInformation();
		 * notification.setNotificationDetails(
		 * "Your order has been placed for product," + " " + product.getProductName());
		 * notification.setNotificationType("specific");
		 * 
		 * Date date = new Date();
		 * 
		 * Calendar calendar = Calendar.getInstance(); calendar.setTime(date);
		 * calendar.add(Calendar.MINUTE, 330);
		 * 
		 * SimpleDateFormat sdfNow = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); String
		 * s1 = sdfNow.format(calendar.getTime());
		 * 
		 * NotificationInformation notificationTwo = new NotificationInformation();
		 * notificationTwo.setNotificationDetails( user.getName() + " " + "has ordered"
		 * + " " + product.getProductName() + " " + "on" + " " + s1);
		 * notificationTwo.setNotificationType("product");
		 * notificationRepo.save(notificationTwo);
		 * user.getNotificaton().add(notification); MyOrderDetails myOrder = new
		 * MyOrderDetails(); myOrder.setName(product.getProductName()); //
		 * myOrder.setImage(product.getProduct_image()); myOrder.setType("product");
		 * myOrder.setPrice(saleCoins); myOrder.setUserMyOrder(user);
		 * user.getMyorder().add(myOrder); myOrderRepository.save(myOrder);
		 * userRepo.save(user); } else { response =
		 * response.concat("Insufficient coins to place order"); } } return response;
		 * }// End of place order method.
		 * 
		 * @Override
		 * 
		 * @Transactional public String placeCartOrder(OrderInformationDto order,
		 * Optional<UserInformation> user) throws FirebaseMessagingException { String
		 * response = "Empty Cart"; List<ProductInformationDto> cartProduct = new
		 * ArrayList<>(); List<ProductInformation> fetchProduct = new
		 * ArrayList<ProductInformation>(); UserInformation fetchedUser =
		 * user.orElseThrow(UserExist::new); List<CartProduct> addedCartProducts =
		 * fetchedUser.getCartProducts(); ProductInformationDto productInformationDto =
		 * new ProductInformationDto(); for (CartProduct addedcartProduct :
		 * addedCartProducts) { ProductInformation productInformation =
		 * productRepo.findById(1 addedcartProduct.getProductId() ).get();
		 * BeanUtils.copyProperties(productInformation, productInformationDto);
		 * productInformationDto.setSize(addedcartProduct.getSize());
		 * productInformationDto.setQuantity(addedcartProduct.getQuantity());
		 * cartProduct.add(productInformationDto); fetchProduct.add(productInformation);
		 * } if (!cartProduct.isEmpty() && order != null) { double totalProductCoins =
		 * 0; cartProduct.stream().map(x -> x.getProductCoins() *
		 * x.getQuantity()).reduce(0.0, Double::sum);
		 * 
		 * double userCoins = myShopService.getCoin(fetchedUser); double saleCoins =
		 * totalProductCoins - (double) (((cartProduct.stream() .map(x ->
		 * x.getDiscount() * x.getQuantity()).reduce(0.0, Double::sum) *
		 * totalProductCoins) / 100)); int countProduct = 0; OrderInformation
		 * orderEntity = new OrderInformation(); BeanUtils.copyProperties(order,
		 * orderEntity); if (userCoins >= saleCoins) {
		 * fetchedUser.getProduct().addAll(fetchProduct); for (ProductInformation
		 * productInformation : fetchProduct) {
		 * productInformation.getOrder().add(orderEntity); }
		 * orderEntity.setOrderUser(fetchedUser); orderRepo.save(orderEntity); double
		 * rewardCoins = fetchedUser.getReward().getRewardCoins(); if (rewardCoins >=
		 * saleCoins) { rewardRepo.updateReward(Double.parseDouble(String.format("%.2f",
		 * (rewardCoins - saleCoins))), fetchedUser.getReward().getRewardId()); } else
		 * if (rewardCoins < saleCoins) {
		 * rewardRepo.updateRewardToZero(fetchedUser.getReward().getRewardId()); double
		 * adminBalanceCoin2 = Double.parseDouble(String.format("%.2f",
		 * fetchedUser.getAdminReward().getAdminRewardCoins() - (saleCoins -
		 * rewardCoins))); adminRewardRepo.updateAdminReward(adminBalanceCoin2,
		 * fetchedUser.getAdminReward().getAdminRewardId()); } response =
		 * "Order placed successfully and balance coins are" + " " +
		 * Double.parseDouble(String.format("%.2f", userCoins - saleCoins));
		 * myShopService.sendMail(fetchedUser.getEmail(), fetchedUser.getName(),
		 * "order"); List<NotificationInformation> notificationInformations = new
		 * ArrayList<NotificationInformation>(); List<MyOrderDetails> myOrders = new
		 * ArrayList<MyOrderDetails>(); for (ProductInformationDto informationDto :
		 * cartProduct) { countProduct++;
		 * notificationInformations.add(addNotification(informationDto,
		 * fetchedUser.getName())); myOrders.add(addOrderDetails(informationDto,
		 * saleCoins, fetchedUser)); }
		 * fetchedUser.getNotificaton().addAll(notificationInformations);
		 * fetchedUser.getMyorder().addAll(myOrders); String newResponse = countProduct
		 * > 1 ? cartProduct.get(0).getProductName() + " and " + (countProduct - 1) +
		 * " More" + " Products " + response : cartProduct.get(0).getProductName() +
		 * response;
		 * firebaseService.sendTokenNotification(fetchedUser.getFirebaseToken(),
		 * newResponse, cartProduct.get(0).getProductImage()); for (CartProduct
		 * addedcartProduct : addedCartProducts) { updateStock(addedcartProduct);
		 * addedcartProduct.setCartProudutUser(null); }
		 * cartProductRepo.deleteAll(addedCartProducts);
		 * fetchedUser.setCartProducts(Collections.emptyList());
		 * userRepo.save(fetchedUser); } else { return
		 * "Insufficient coins to place order"; } } return response; }
		 * 
		 * @Transactional private void updateStock(CartProduct cartProduct) {
		 * ProductInformation productInformation = productRepo.findById(1
		 * cartProduct.getProductId() ).get(); List<ProductSizeStock> productSizeStocks
		 * = productInformation.getProductSizeStocks(); ProductSizeStock
		 * productSizeStock = productSizeStocks.stream() .filter(x ->
		 * x.getSize().equals(cartProduct.getSize())).findFirst().get();
		 * productSizeStock.setStockAvailable(productSizeStock.getStockAvailable() -
		 * cartProduct.getQuantity()); productSizeStocks.remove(productSizeStock);
		 * productSizeStocks.add(productSizeStock);
		 * productInformation.setProductSizeStocks(productSizeStocks);
		 * productRepo.save(productInformation); }
		 * 
		 * @Transactional private NotificationInformation
		 * addNotification(ProductInformationDto informationDto, String userName) {
		 * NotificationInformation notification = new NotificationInformation();
		 * notification.setNotificationDetails(
		 * "Your order has been placed for product," + " " +
		 * informationDto.getProductName());
		 * notification.setNotificationType("specific");
		 * notification.setNotificationImage(informationDto.getProductImage()); Date
		 * date = new Date(); Calendar calendar = Calendar.getInstance();
		 * calendar.setTime(date); calendar.add(Calendar.MINUTE, 330); SimpleDateFormat
		 * sdfNow = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); String s1 =
		 * sdfNow.format(calendar.getTime()); NotificationInformation notificationTwo =
		 * new NotificationInformation(); notificationTwo.setNotificationDetails(
		 * userName + " " + "has ordered" + " " + informationDto.getProductName() + " "
		 * + "on" + " " + s1);
		 * notificationTwo.setNotificationImage(informationDto.getProductImage());
		 * notificationTwo.setNotificationType("product");
		 * notificationRepo.save(notificationTwo); return notification; }
		 * 
		 * @Transactional private MyOrderDetails addOrderDetails(ProductInformationDto
		 * productInformationDto, UserInformation user) { MyOrderDetails myOrder = new
		 * MyOrderDetails(); myOrder.setName(productInformationDto.getProductName());
		 * myOrder.setImage(productInformationDto.getProductImage());
		 * myOrder.setType("product");
		 * myOrder.setPrice(productInformationDto.getProductPrice());
		 * myOrder.setPaidProductPrice(productInformationDto.getFinalPrice());
		 * myOrder.setSize(productInformationDto.getSize());
		 * myOrder.setQuantity(productInformationDto.getQuantity());
		 * myOrder.setUserMyOrder(user); return myOrderRepository.save(myOrder); }
		 * 
		 * @Override
		 * 
		 * @Transactional public String placeCartOrderTwo(OrderCartPlaceDto
		 * cartPlaceDto) throws FirebaseMessagingException { String response = "";
		 * OrderInformationDto order = cartPlaceDto.getOrder();
		 * List<ProductInformationDto> cartProduct = new
		 * ArrayList<ProductInformationDto>(); List<ProductInformation> fetchProduct =
		 * new ArrayList<ProductInformation>(); UserInformation fetchedUser =
		 * userRepo.findById(cartPlaceDto.getUserId()).orElseThrow(UserExist::new);
		 * List<CartProduct> addedCartProducts = fetchedUser.getCartProducts(); for
		 * (CartProduct addedcartProduct : addedCartProducts) { ProductInformationDto
		 * productInformationDto = new ProductInformationDto(); ProductInformation
		 * productInformation = productRepo.findById(1 addedcartProduct.getProductId()
		 * ).get(); BeanUtils.copyProperties(productInformation, productInformationDto);
		 * productInformationDto.setSize(addedcartProduct.getSize());
		 * productInformationDto.setQuantity(addedcartProduct.getQuantity());
		 * cartProduct.add(productInformationDto); fetchProduct.add(productInformation);
		 * } if (!cartProduct.isEmpty() && order != null) { int countProduct = 0;
		 * OrderInformation orderEntity = new OrderInformation();
		 * BeanUtils.copyProperties(order, orderEntity);
		 * fetchedUser.getProduct().addAll(fetchProduct); for (ProductInformation
		 * productInformation : fetchProduct) {
		 * productInformation.getOrder().add(orderEntity); }
		 * orderEntity.setOrderUser(fetchedUser); orderRepo.save(orderEntity); response
		 * = "Order placed successfully and balance coins are" + " ";
		 * myShopService.sendMail(fetchedUser.getEmail(), fetchedUser.getName(),
		 * "order"); List<NotificationInformation> notificationInformations = new
		 * ArrayList<NotificationInformation>(); List<MyOrderDetails> myOrders = new
		 * ArrayList<MyOrderDetails>(); for (ProductInformationDto informationDto :
		 * cartProduct) { countProduct++;
		 * notificationInformations.add(addNotification(informationDto,
		 * fetchedUser.getName())); myOrders.add(addOrderDetails(informationDto,
		 * fetchedUser)); }
		 * fetchedUser.getNotificaton().addAll(notificationInformations);
		 * fetchedUser.getMyorder().addAll(myOrders); String newResponse = countProduct
		 * > 1 ? cartProduct.get(0).getProductName() + " and " + (countProduct - 1) +
		 * " More" + " Products " + response : cartProduct.get(0).getProductName() +
		 * response;
		 * firebaseService.sendTokenNotification(fetchedUser.getFirebaseToken(),
		 * newResponse, cartProduct.get(0).getProductImage()); //
		 * cartProduct.removeAll(cartProduct); for (CartProduct addedcartProduct :
		 * addedCartProducts) { updateStock(addedcartProduct);
		 * addedcartProduct.setCartProudutUser(null); }
		 * cartProductRepo.deleteAll(addedCartProducts);
		 * fetchedUser.setCartProducts(Collections.emptyList());
		 * userRepo.save(fetchedUser); } return response; }
		 * 
		 * } // End of BuyProductServiceImple class.
		 */