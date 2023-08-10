package com.banking.dto;

import lombok.Data;

@Data
public class AccountDTO {
	private Long id;
	private String accountNumber;
	private Integer accountType;
	private Double balance;
	private String status;
	private Long customer;

	private double depositAmount;
	private double withdrawalAmount;
}
