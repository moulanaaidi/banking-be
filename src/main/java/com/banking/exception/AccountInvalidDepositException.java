package com.banking.exception;

public class AccountInvalidDepositException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountInvalidDepositException(String message) {
		super(message);
	}

	public AccountInvalidDepositException(String message, Throwable cause) {
		super(message, cause);
	}

}
