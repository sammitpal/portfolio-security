package com.security.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	   @ExceptionHandler(BadCredentials.class)
	    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentials e) {
	        ErrorResponse response = new ErrorResponse();
	        response.setTimestamp(new Date().toLocaleString());
	        response.setHttpStatus(HttpStatus.BAD_REQUEST);
	        response.setMessage(e.getMessage());
	        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
	    }
	   
	   @ExceptionHandler(RegistrationException.class)
	    public ResponseEntity<ErrorResponse> handelFeignProxyException(RegistrationException e) {
	        ErrorResponse response = new ErrorResponse();
	        response.setTimestamp(new Date().toLocaleString());
	        response.setHttpStatus(HttpStatus.CONFLICT);
	        response.setMessage(e.getMessage());
	        return new ResponseEntity<ErrorResponse>(response, HttpStatus.CONFLICT);
	    }
	   
	

}
