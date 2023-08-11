package com.banking.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.banking.constant.AccountStatus;
import com.banking.dto.AccountDTO;
import com.banking.exception.*;
import com.banking.model.Account;
import com.banking.model.AccountType;
import com.banking.repository.AccountRepository;
import com.banking.repository.AccountTypeRepository;

@SpringBootTest
@Transactional
@Rollback
public class AccountServiceImplTest {

	private AccountServiceImpl accountService;
	private AccountRepository accountRepositoryMock;
	private AccountTypeRepository accountTypeRepositoryMock;
	private ModelMapper modelMapper;

	@BeforeEach
	public void setup() {
		accountRepositoryMock = mock(AccountRepository.class);
		accountTypeRepositoryMock = mock(AccountTypeRepository.class);
		modelMapper = new ModelMapper();
		accountService = new AccountServiceImpl(accountRepositoryMock, accountTypeRepositoryMock, modelMapper);
	}

	@Test
	public void testCreateAccount() {
		// Arrange
		Integer accountType = 1;
		String generatedAccountNumber = "12345678901234567890";
		AccountType accountTypeEntity = new AccountType();
		accountTypeEntity.setId(accountType);

		when(accountTypeRepositoryMock.findById(accountType)).thenReturn(java.util.Optional.of(accountTypeEntity));
		when(accountRepositoryMock.existsByAccountNumber(anyString())).thenReturn(false);

		Account savedAccount = new Account();
		savedAccount.setAccountNumber(generatedAccountNumber);
		savedAccount.setAccountType(accountTypeEntity);
		savedAccount.setStatus(AccountStatus.ACTIVE.toString());

		when(accountRepositoryMock.save(any())).thenReturn(savedAccount);

		// Act
		AccountDTO createdAccountDTO = accountService.create(accountType);

		// Assert
		assertNotNull(createdAccountDTO);
		assertEquals(savedAccount.getAccountNumber(), createdAccountDTO.getAccountNumber());
		assertEquals(savedAccount.getAccountType().getId(), createdAccountDTO.getAccountType());
		assertEquals(savedAccount.getStatus(), createdAccountDTO.getStatus());
	}

	@Test
	public void testCreateAccountWithInvalidAccountType() {
		// Arrange
		int invalidAccountType = 999;

		when(accountTypeRepositoryMock.findById(invalidAccountType)).thenReturn(java.util.Optional.empty());

		// Act and Assert
		assertThrows(AccountTypeInvalidException.class, () -> accountService.create(invalidAccountType));
	}

	@Test
	public void testDepositCash() {
		// Arrange
		String accountNumber = "1234567890";
		double depositAmount = 500.0;
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setBalance(100.0); // Existing balance

		when(accountRepositoryMock.findByAccountNumber(accountNumber)).thenReturn(account);

		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setAccountNumber(accountNumber);
		accountDTO.setDepositAmount(depositAmount);
		// Act
		accountService.depositCash(accountDTO);

		// Assert
		assertEquals(600.0, account.getBalance()); // 100 + 500
	}

	@Test
	public void testDepositCashWithInvalidDepositAmount() {
		// Arrange
		String accountNumber = "12345678901234567890";
		double invalidDepositAmount = -100.0;

		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setAccountNumber(accountNumber);
		accountDTO.setDepositAmount(invalidDepositAmount);

		// Act and Assert
		assertThrows(AccountInvalidDepositException.class, () -> accountService.depositCash(accountDTO));
	}

	// More test methods for withdrawCash, close, and inquire

	@Test
	public void testInquireExistingAccount() {
		// Arrange
		String accountNumber = "1234567890";
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setBalance(1000.0);

		when(accountRepositoryMock.findByAccountNumber(accountNumber)).thenReturn(account);

		// Act
		AccountDTO accountDTO = accountService.inquire(accountNumber);

		// Assert
		assertNotNull(accountDTO);
		assertEquals(account.getAccountNumber(), accountDTO.getAccountNumber());
		assertEquals(account.getBalance(), accountDTO.getBalance());
	}

	@Test
	public void testInquireNonExistingAccount() {
		// Arrange
		String nonExistingAccountNumber = "9999999999";

		when(accountRepositoryMock.findByAccountNumber(nonExistingAccountNumber)).thenReturn(null);

		// Act and Assert
		assertThrows(AccountNotFoundException.class, () -> accountService.inquire(nonExistingAccountNumber));
	}
}
