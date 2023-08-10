package com.banking.exception;

public class CustomerCreateException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerCreateException(String message) {
        super(message);
    }

    public CustomerCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}