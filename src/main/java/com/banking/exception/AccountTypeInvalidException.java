package com.banking.exception;

public class AccountTypeInvalidException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountTypeInvalidException(String message) {
		super(message);
	}

	public AccountTypeInvalidException(String message, Throwable cause) {
		super(message, cause);
	}
}
