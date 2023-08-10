package com.banking.dto;

import lombok.Data;

@Data
public class CustomerDTO {
	private Long id;
	private String name;
	private String documentNumber;
	private Integer documentTypeId;
}
