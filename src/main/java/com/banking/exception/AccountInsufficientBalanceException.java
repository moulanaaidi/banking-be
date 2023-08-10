package com.banking.exception;

public class AccountInsufficientBalanceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountInsufficientBalanceException(String message) {
		super(message);
	}

	public AccountInsufficientBalanceException(String message, Throwable cause) {
		super(message, cause);
	}

}
