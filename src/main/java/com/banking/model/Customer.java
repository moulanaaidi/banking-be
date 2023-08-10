package com.banking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
	@SequenceGenerator(name = "customer_seq", sequenceName = "customer_id_seq", allocationSize = 1, initialValue = 100000)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", length = 255)
	private String name;

	@Column(name = "document_number", length = 50, unique = true) // Add unique constraint
	private String documentNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "document_type_id")
	private DocumentType documentType;
}
