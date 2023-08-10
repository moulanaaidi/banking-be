package com.banking.service;

import com.banking.dto.CustomerDTO;

public interface CustomerService {
	
	CustomerDTO create(CustomerDTO customerDTO);

	CustomerDTO inquire(Long customerId);

}
