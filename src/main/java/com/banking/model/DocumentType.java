package com.banking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "document_type")
public class DocumentType {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "name", nullable = false, length = 50)
	private String name;
}