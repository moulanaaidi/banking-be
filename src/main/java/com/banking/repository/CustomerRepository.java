package com.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	boolean existsByDocumentNumber(String documentNumber);
}
