package com.banking.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CustomerCreateRequest {
	@NotBlank(message = "Name is required")
	@Size(max = 255, message = "Name can't exceed {max} characters")
	private String name;

	@NotBlank(message = "Document number is required")
	@Size(max = 50, message = "Document number can't exceed {max} characters")
	private String documentNumber;

	@NotBlank(message = "Document type ID is required")
	private String documentTypeId;
}
