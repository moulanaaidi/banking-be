package com.banking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
	@SequenceGenerator(name = "account_seq", sequenceName = "account_id_seq", allocationSize = 1, initialValue = 100000)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "accountNumber", length = 20, unique = true) // Make accountNumber unique
	private String accountNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accountTypeId")
	private AccountType accountType;

	@Column(name = "balance")
	private Double balance;

	@Column(name = "status", length = 50)
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

}
