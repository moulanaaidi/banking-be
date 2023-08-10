package com.banking.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.banking.repository.CustomerRepository;

public class ExistingCustomerIdValidator implements ConstraintValidator<ExistingCustomerId, Long> {

	private final CustomerRepository customerRepository; // Inject your CustomerRepository here

	public ExistingCustomerIdValidator(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public boolean isValid(Long customerId, ConstraintValidatorContext context) {
		return customerId != null && customerId > 0 && customerRepository.existsById(customerId);
	}
}
