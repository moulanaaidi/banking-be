package com.banking.exception;

public class AccountWithdrawalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountWithdrawalException(String message) {
		super(message);
	}

	public AccountWithdrawalException(String message, Throwable cause) {
		super(message, cause);
	}

}
