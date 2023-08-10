package com.banking.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.dto.CustomerDTO;
import com.banking.exception.CustomerCreateException;
import com.banking.exception.CustomerNotFoundException;
import com.banking.model.Customer;
import com.banking.repository.CustomerRepository;
import com.banking.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER = LogManager.getLogger(CustomerServiceImpl.class);

	private final CustomerRepository customerRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
		this.customerRepository = customerRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional
	public CustomerDTO create(CustomerDTO customerDTO) {
		String documentNumber = customerDTO.getDocumentNumber();

		// Check if the document number already exists in the database
		if (customerRepository.existsByDocumentNumber(documentNumber)) {
			throw new CustomerCreateException("A customer with this document number already exists.");
		}

		Customer customer = modelMapper.map(customerDTO, Customer.class);
		Customer savedCustomer = customerRepository.save(customer);
		return modelMapper.map(savedCustomer, CustomerDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public CustomerDTO inquire(Long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with given id."));

		return modelMapper.map(customer, CustomerDTO.class);
	}

}