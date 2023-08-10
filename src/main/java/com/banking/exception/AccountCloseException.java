package com.banking.exception;

public class AccountCloseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountCloseException(String message) {
		super(message);
	}

	public AccountCloseException(String message, Throwable cause) {
		super(message, cause);
	}

}
