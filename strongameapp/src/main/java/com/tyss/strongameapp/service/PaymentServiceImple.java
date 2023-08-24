package com.tyss.strongameapp.service;

import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.tyss.strongameapp.dto.RazorpayResponse;
import com.tyss.strongameapp.repository.PaymentRepository;

/**
 * PaymentServiceImple is implementation class which is used for payment
 * management.
 * 
 * @author Sushma Guttal
 *
 */

@Service
public class PaymentServiceImple implements PaymentService {

	/**
	 * THis field is used to invoke persistence layer methods.
	 */
	@Autowired
	PaymentRepository paymentRepository;

//	@Value("${algorithm}")
	private static String hmacSha256Alorithm = "HmacSHA256";

	private String generatedSignature;

//	@Value("${keyid}")
	private static String keyId = "rzp_live_DJTelLI0VIfF5y";

//	@Value("${keysecret}")
	private static String keySecret = "8HUuFSexlFbhmhb11gj4YzCx";

	/**
	 * This method is implemented to generate order id.
	 * 
	 * @param amount
	 * @param currency
	 * @return String
	 */
	public String getOrderId(double amount, String currency) {

		Order order = null;

		String id = null;

		try {

			RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", amount); // amount in the smallest currency unit
			orderRequest.put("currency", currency);
			/**
			 * orderRequest.put("receipt", "order_rcptid_11");
			 */

			order = razorpayClient.Orders.create(orderRequest);

			JSONObject jsonObject = new JSONObject(String.valueOf(order));
			id = jsonObject.getString("id");

		} catch (RazorpayException e) {
			// Handle Exception
			e.getMessage();
		}
		return id;

	}// End of getOrderId method.

	/**
	 * This method is implemented to verify the signature.
	 * 
	 * @param data
	 * @return boolean
	 */
	public boolean verifySignature(RazorpayResponse data) {

		try {
			generatedSignature = calculateRFC2104HMAC(data.getRazorpay_order_id() + "|" + data.getRazorpay_payment_id(),
					keySecret);

		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return generatedSignature.equals(data.getRazorpay_signature());

	}// End of verifySignature method.

	/**
	 * This method is implemented to calculateRFC2104HMAC.
	 * 
	 * @param data
	 * @param secret
	 * @return String
	 * @throws java.security.SignatureException
	 */
	public static String calculateRFC2104HMAC(String data, String secret) throws java.security.SignatureException {
		String result;
		try {

			// get an hmac_sha256 key from the raw secret bytes
			SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), hmacSha256Alorithm);

			// get an hmac_sha256 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance(hmacSha256Alorithm);
			mac.init(signingKey);

			// compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(data.getBytes());

			// base64-encode the hmac
			result = DatatypeConverter.printHexBinary(rawHmac).toLowerCase();

		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
	}// End of calculateRFC2104HMAC method

}// End of PaymentServiceImple class.
