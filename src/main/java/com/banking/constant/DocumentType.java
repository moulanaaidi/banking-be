package com.banking.constant;

public enum DocumentType {
	IDENTIFICATION_CARD(1, "Identification Card"), PASSPORT(2, "Passport");

	private final int id;
	private final String name;

	DocumentType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
