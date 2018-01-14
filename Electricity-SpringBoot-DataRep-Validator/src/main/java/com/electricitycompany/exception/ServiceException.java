package com.electricitycompany.exception;

import org.springframework.validation.Errors;

@SuppressWarnings("serial")
public class ServiceException extends Exception {
	private Errors errors;

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public ServiceException(Errors errors) {
		this.errors = errors;
	}
}
