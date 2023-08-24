
package com.tyss.strongameapp.exceptionhandler;

import java.util.Date;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.tyss.strongameapp.exception.ErrorDetails;
import com.tyss.strongameapp.exception.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@ControllerAdvice

@Order(2)
public class GlobalExceptionHandler {

	// handling specific exception

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> resourceNotFoundHandling(ResourceNotFoundException exception,
			WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Oops..!Operation Failed.",
				request.getDescription(false), true);
		log.error(exception.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	// handling global exception

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> globalExceptionHandling(Exception exception, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Oops..!Operation Failed.",
				request.getDescription(false), true);
		log.error(exception.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
