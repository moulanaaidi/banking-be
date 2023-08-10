package com.banking.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerInquireResponse {

	private String message;
	private Long id;
	private String name;
	private String documentNumber;
	private Integer documentTypeId;

	public CustomerInquireResponse(String message, Long id, String name, String documentNumber,
			Integer documentTypeId) {
		this.message = message;
		this.id = id;
		this.name = name;
		this.documentNumber = documentNumber;
		this.documentTypeId = documentTypeId;
	}
}
