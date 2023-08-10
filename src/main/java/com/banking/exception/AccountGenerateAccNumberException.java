package com.banking.exception;

public class AccountGenerateAccNumberException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountGenerateAccNumberException(String message) {
		super(message);
	}

	public AccountGenerateAccNumberException(String message, Throwable cause) {
		super(message, cause);
	}

}
