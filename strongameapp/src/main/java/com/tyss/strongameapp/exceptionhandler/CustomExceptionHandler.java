package com.tyss.strongameapp.exceptionhandler;

import java.util.Date;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.tyss.strongameapp.dto.ResponseDto;
import com.tyss.strongameapp.exception.AddressLabelTypeException;
import com.tyss.strongameapp.exception.AddressTypeException;
import com.tyss.strongameapp.exception.CoachException;
import com.tyss.strongameapp.exception.CoachNotFoundException;
import com.tyss.strongameapp.exception.CoachReviewException;
import com.tyss.strongameapp.exception.ErrorDetails;
import com.tyss.strongameapp.exception.FireBaseDeviceNotRegister;
import com.tyss.strongameapp.exception.InvalidRatingException;
import com.tyss.strongameapp.exception.ModuleContentException;
import com.tyss.strongameapp.exception.OperationAccessDeniedException;
import com.tyss.strongameapp.exception.PackageException;
import com.tyss.strongameapp.exception.PlanException;
import com.tyss.strongameapp.exception.ProductException;
import com.tyss.strongameapp.exception.ProductNotFound;
import com.tyss.strongameapp.exception.SessionException;
import com.tyss.strongameapp.exception.UserException;
import com.tyss.strongameapp.exception.UserNotExistException;
import com.tyss.strongameapp.exception.UserNotFoundException;
import com.tyss.strongameapp.exception.UserPlanException;

@RestControllerAdvice
@Order(1)
public class CustomExceptionHandler {

	@ExceptionHandler(ModuleContentException.class)
	public ResponseEntity<ErrorDetails> moduleContentExceptionHandler(ModuleContentException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),
				true);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserNotExistException.class)
	public ResponseEntity<ErrorDetails> userNotExistExceptionHandler(UserNotExistException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),
				true);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorDetails> userExceptionHandler(UserException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),
				true);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(ProductNotFound.class)
	public ResponseEntity<ErrorDetails> productNotFoundHandler(ProductNotFound exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),
				true);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ProductException.class)
	public ResponseEntity<ErrorDetails> productExceptionHandler(ProductException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),
				true);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(OperationAccessDeniedException.class)
	public ResponseEntity<ErrorDetails> accessDeniedExceptionHandler(OperationAccessDeniedException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),
				true);
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CoachException.class)
	public ResponseEntity<ResponseDto> coachExceptionHandler(CoachException exception) {
		ResponseDto response = new ResponseDto();
		response.setError(false);
		response.setMessage(exception.getMessage());
		response.setData(response);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(PlanException.class)
	public ResponseEntity<ResponseDto> planExceptionHandler(PlanException exception) {
		ResponseDto response = new ResponseDto();
		response.setError(false);
		response.setMessage(exception.getMessage());
		response.setData(response);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CoachNotFoundException.class)
	public ResponseEntity<ErrorDetails> coachNotFoundExceptionHandler(CoachNotFoundException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),
				true);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ResponseDto> userNotExistHandler(UserNotFoundException userNotFoundException) {
		ResponseDto responseDTO = new ResponseDto();
		responseDTO.setError(true);
		responseDTO.setData(null);
		responseDTO.setMessage(userNotFoundException.getMessage());
		return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AddressTypeException.class)
	public ResponseEntity<ResponseDto> addressTypeExceptionHandler(AddressTypeException addressTypeException) {
		ResponseDto responseDTO = new ResponseDto();
		responseDTO.setError(true);
		responseDTO.setData(null);
		responseDTO.setMessage(addressTypeException.getMessage());
		return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AddressLabelTypeException.class)
	public ResponseEntity<ResponseDto> addressLabelTypeHandler(AddressLabelTypeException addressLabelTypeException) {
		ResponseDto responseDTO = new ResponseDto();
		responseDTO.setError(true);
		responseDTO.setData(null);
		responseDTO.setMessage(addressLabelTypeException.getMessage());
		return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CoachReviewException.class)
	public ResponseEntity<ResponseDto> coachReviewExceptionHandler(CoachReviewException coachReviewException) {
		ResponseDto responseDTO = new ResponseDto();
		responseDTO.setError(true);
		responseDTO.setData(null);
		responseDTO.setMessage(coachReviewException.getMessage());
		return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidRatingException.class)
	public ResponseEntity<ResponseDto> invalidRatingExceptionHandler(InvalidRatingException invalidRatingException) {
		ResponseDto responseDTO = new ResponseDto();
		responseDTO.setError(true);
		responseDTO.setData(null);
		responseDTO.setMessage(invalidRatingException.getMessage());
		return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserPlanException.class)
	public ResponseEntity<ResponseDto> userPlanExceptionHandler(UserPlanException userPlanException) {
		ResponseDto responseDTO = new ResponseDto();
		responseDTO.setError(true);
		responseDTO.setData(null);
		responseDTO.setMessage(userPlanException.getMessage());
		return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PackageException.class)
	public ResponseEntity<ErrorDetails> packageExceptionHandler(PackageException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),
				true);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SessionException.class)
	public ResponseEntity<ErrorDetails> sessionExceptionHandler(SessionException exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),
				true);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * Fire-base exception handler
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(FirebaseMessagingException.class)
	public ResponseEntity<ResponseDto> firebaseMessagingExceptionHandler(FirebaseMessagingException exception) {
		ResponseDto responseDTO = new ResponseDto();
		responseDTO.setError(true);
		responseDTO.setData(null);
		responseDTO.setMessage("Error In FireBaseNotification");
		return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Fire-base exception handler
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(FireBaseDeviceNotRegister.class)
	public ResponseEntity<ResponseDto> fireBaseDeviceNotRegisterHnadler(FireBaseDeviceNotRegister exception) {
		ResponseDto responseDTO = new ResponseDto();
		responseDTO.setError(true);
		responseDTO.setData(null);
		responseDTO.setMessage(exception.getMessage());
		return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * 
	 * BadCredentials exception handler
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDetails> badCredentialsHandling(BadCredentialsException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				request.getDescription(false), true);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * 
	 * DisabledException exception handler
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<ErrorDetails> disabledExceptionHandling(DisabledException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				request.getDescription(false), true);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	
	/**
	 * 
	 * UsernameNotFound exception handler
	 * 
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorDetails> disabledExceptionHandling(UsernameNotFoundException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				request.getDescription(false), true);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
}
