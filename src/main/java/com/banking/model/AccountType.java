package com.banking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "account_type")
public class AccountType {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "name", nullable = false, length = 50)
	private String name;
}