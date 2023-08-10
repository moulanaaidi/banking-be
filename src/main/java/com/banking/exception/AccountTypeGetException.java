package com.banking.exception;

public class AccountTypeGetException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountTypeGetException(String message) {
		super(message);
	}

	public AccountTypeGetException(String message, Throwable cause) {
		super(message, cause);
	}

}
