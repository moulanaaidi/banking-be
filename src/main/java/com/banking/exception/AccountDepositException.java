package com.banking.exception;

public class AccountDepositException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountDepositException(String message) {
		super(message);
	}

	public AccountDepositException(String message, Throwable cause) {
		super(message, cause);
	}
}
