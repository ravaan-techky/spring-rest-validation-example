package com.ravaan.techky.spring.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ravaan.techky.spring.exception.dto.ExceptionResponse;

/**
 * The Class CustomResponseEntityExceptionHandler.
 */
@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * Handle all exception.
	 *
	 * @param exception the exception
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception exception, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getLocalizedMessage(), request.getDescription(true));
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	/**
	 * Handle method argument not valid.
	 *
	 * @param exception the exception
	 * @param headers the headers
	 * @param status the status
	 * @param request the request
	 * @return the response entity
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder fieldErrorBuilder = new StringBuilder();
		exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
			fieldErrorBuilder.append("Field " + fieldError.getField() + " value " + fieldError.getRejectedValue() + " is rejected because of " + fieldError.getDefaultMessage());
		});
		ExceptionResponse exceptionResponse = new ExceptionResponse("Validation Fail. " + fieldErrorBuilder.toString(), exception.getBindingResult().toString());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
