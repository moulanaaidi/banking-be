package com.banking.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.banking.dto.CustomerDTO;
import com.banking.exception.CustomerCreateException;
import com.banking.exception.CustomerNotFoundException;
import com.banking.model.Customer;
import com.banking.repository.CustomerRepository;

@SpringBootTest
@Transactional
@Rollback
public class CustomerServiceImplTest {

	private CustomerServiceImpl customerService;
	private CustomerRepository customerRepositoryMock;
	private ModelMapper modelMapper;

	@BeforeEach
	public void setup() {
		customerRepositoryMock = mock(CustomerRepository.class);
		modelMapper = new ModelMapper();
		customerService = new CustomerServiceImpl(customerRepositoryMock, modelMapper);
	}

	@Test
	public void testCreateCustomer() {
		// Arrange
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setDocumentNumber("12345678901234567890"); // Sample document number

		when(customerRepositoryMock.existsByDocumentNumber(customerDTO.getDocumentNumber())).thenReturn(false);

		Customer savedCustomer = new Customer();
		when(customerRepositoryMock.save(any())).thenReturn(savedCustomer);

		// Act
		CustomerDTO createdCustomerDTO = customerService.create(customerDTO);

		// Assert
		assertNotNull(createdCustomerDTO);
		assertEquals(savedCustomer.getDocumentNumber(), createdCustomerDTO.getDocumentNumber());
		// Add more assertions as needed
	}

	@Test
	public void testCreateCustomerWithExistingDocumentNumber() {
		// Arrange
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setDocumentNumber("1234567890"); // Sample document number

		when(customerRepositoryMock.existsByDocumentNumber(customerDTO.getDocumentNumber())).thenReturn(true);

		// Act and Assert
		assertThrows(CustomerCreateException.class, () -> customerService.create(customerDTO));
	}

	@Test
	public void testInquireExistingCustomer() {
		// Arrange
		Long customerId = 1L;
		Customer existingCustomer = new Customer();
		existingCustomer.setId(customerId);
		existingCustomer.setDocumentNumber("1234567890"); // Sample document number

		when(customerRepositoryMock.findById(customerId)).thenReturn(java.util.Optional.of(existingCustomer));

		// Act
		CustomerDTO customerDTO = customerService.inquire(customerId);

		// Assert
		assertNotNull(customerDTO);
		assertEquals(existingCustomer.getDocumentNumber(), customerDTO.getDocumentNumber());
		// Add more assertions as needed
	}

	@Test
	public void testInquireNonExistingCustomer() {
		// Arrange
		Long customerId = 1L;

		when(customerRepositoryMock.findById(customerId)).thenReturn(java.util.Optional.empty());

		// Act and Assert
		assertThrows(CustomerNotFoundException.class, () -> customerService.inquire(customerId));
	}
}
