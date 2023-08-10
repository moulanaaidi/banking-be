package com.banking.util;

import java.security.SecureRandom;
import java.util.Random;

public class AccountNumberGenerator {

	private static final String NUMERIC_CHARACTERS = "0123456789";
	private static final int ACCOUNT_NUMBER_LENGTH = 20;

	private static final Random random = new SecureRandom();

	public static String generateRandomAccountNumber() {
		StringBuilder accountNumberBuilder = new StringBuilder(ACCOUNT_NUMBER_LENGTH);
		for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
			int randomIndex = random.nextInt(NUMERIC_CHARACTERS.length());
			char randomCharacter = NUMERIC_CHARACTERS.charAt(randomIndex);
			accountNumberBuilder.append(randomCharacter);
		}
		return accountNumberBuilder.toString();
	}

	public static void main(String[] args) {
		// Example usage
		String uniqueAccountNumber = generateRandomAccountNumber();
		System.out.println("Generated Unique Account Number: " + uniqueAccountNumber);
	}
}
