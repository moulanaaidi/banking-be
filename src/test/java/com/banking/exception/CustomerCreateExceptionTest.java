package com.banking.exception;

public class CustomerCreateExceptionTest extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerCreateExceptionTest(String message) {
		super(message);
	}

	public CustomerCreateExceptionTest(String message, Throwable cause) {
		super(message, cause);
	}
}