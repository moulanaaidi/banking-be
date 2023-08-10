package com.banking.exception;

public class AccountInquireException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountInquireException(String message) {
		super(message);
	}

	public AccountInquireException(String message, Throwable cause) {
		super(message, cause);
	}

}
