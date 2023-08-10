package com.banking.controller.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dto.CustomerDTO;
import com.banking.dto.request.CustomerCreateRequest;
import com.banking.dto.response.CustomerCreateResponse;
import com.banking.dto.response.CustomerInquireResponse;
import com.banking.exception.CustomerCreateException;
import com.banking.exception.CustomerNotFoundException;
import com.banking.service.CustomerService;
import com.banking.validation.ExistingCustomerId;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	private static final Logger LOGGER = LogManager.getLogger(CustomerController.class);

	private final CustomerService customerService;
	private final ModelMapper modelMapper;

	@Autowired
	public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
		this.customerService = customerService;
		this.modelMapper = modelMapper;
	}

	@PostMapping("/create")
	public ResponseEntity<CustomerCreateResponse> createCustomer(@Validated @RequestBody CustomerCreateRequest req,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
			LOGGER.warn("Validation errors: {}", errors);
			return ResponseEntity.badRequest()
					.body(new CustomerCreateResponse("Validation failed: " + String.join(", ", errors)));
		}

		try {
			LOGGER.info("Creating customer: {}", req);
			customerService.create(modelMapper.map(req, CustomerDTO.class));
			LOGGER.info("Customer created successfully: {}", req);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new CustomerCreateResponse("Customer created successfully."));
		} catch (CustomerCreateException ex) {
			LOGGER.error("Failed to create customer: {}", ex.getMessage(), ex);
			return ResponseEntity.badRequest()
					.body(new CustomerCreateResponse("Failed to create customer: " + ex.getMessage()));
		} catch (Exception ex) {
			LOGGER.error("An error occurred while creating customer: {}", ex.getMessage(), ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new CustomerCreateResponse("Failed to create customer. Please try again later"));
		}
	}

	@GetMapping("/inquire/{customerId}")
	public ResponseEntity<CustomerInquireResponse> inquireCustomer(@PathVariable @ExistingCustomerId Long customerId) {
		LOGGER.info("Inquiring about customer with customerId: {}", customerId);

		try {
			CustomerDTO customerDTO = customerService.inquire(customerId);
			LOGGER.info("Customer inquiry successful for customerId: {}", customerId);
			CustomerInquireResponse res = modelMapper.map(customerDTO, CustomerInquireResponse.class);
			res.setMessage("Customer inquiry successful.");
			return ResponseEntity.ok(res);
		} catch (CustomerNotFoundException e) {
			LOGGER.warn("Customer not found with customerId: {}", customerId, e);
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new CustomerInquireResponse(e.getMessage(), customerId, null, null, null));
		} catch (Exception e) {
			LOGGER.error("An error occurred while inquiring about the customer with customerId: {}", customerId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomerInquireResponse(
					"Failed to inquire about the customer. Please try again later.", customerId, null, null, null));
		}
	}

}
