package com.banking.exception;

public class AccountInvalidWithdrawalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountInvalidWithdrawalException(String message) {
		super(message);
	}

	public AccountInvalidWithdrawalException(String message, Throwable cause) {
		super(message, cause);
	}

}
