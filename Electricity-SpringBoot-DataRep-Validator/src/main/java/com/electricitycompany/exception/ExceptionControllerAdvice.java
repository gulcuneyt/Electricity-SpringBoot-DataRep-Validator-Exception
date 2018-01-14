package com.electricitycompany.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<List<ErrorMessage>> handleException(Exception e) {
		List<ErrorMessage> errorMessages = new ArrayList<>();
		if (e instanceof ServiceException) {
			Errors errors = ((ServiceException) e).getErrors();
			errors.getAllErrors().forEach((ObjectError error) -> {
				ErrorMessage em = new ErrorMessage();
				em.setCode(error.getCode());
				em.setMessage(messageSource.getMessage(error.getCode(), error.getArguments(), Locale.US));
				errorMessages.add(em);
			});
		} else {
			ErrorMessage em = new ErrorMessage();
			em.setCode("general.error");
			em.setMessage(e.getMessage());
			errorMessages.add(em);
		}
		return new ResponseEntity<List<ErrorMessage>>(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
