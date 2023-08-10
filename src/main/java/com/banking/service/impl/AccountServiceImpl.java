package com.banking.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banking.constant.AccountStatus;
import com.banking.dto.AccountDTO;
import com.banking.exception.AccountCloseException;
import com.banking.exception.AccountNotFoundException;
import com.banking.exception.AccountDepositException;
import com.banking.exception.AccountGenerateAccNumberException;
import com.banking.exception.AccountTypeGetException;
import com.banking.exception.AccountInquireException;
import com.banking.exception.AccountInsufficientBalanceException;
import com.banking.exception.AccountTypeInvalidException;
import com.banking.exception.AccountInvalidDepositException;
import com.banking.exception.AccountInvalidWithdrawalException;
import com.banking.exception.AccountWithdrawalException;
import com.banking.model.Account;
import com.banking.model.AccountType;
import com.banking.repository.AccountRepository;
import com.banking.repository.AccountTypeRepository;
import com.banking.service.AccountService;
import com.banking.util.AccountNumberGenerator;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger LOGGER = LogManager.getLogger(CustomerServiceImpl.class);

	private final AccountRepository accountRepository;
	private final AccountTypeRepository accountTypeRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public AccountServiceImpl(AccountRepository accountRepository, AccountTypeRepository accountTypeRepository,
			ModelMapper modelMapper) {
		this.accountRepository = accountRepository;
		this.accountTypeRepository = accountTypeRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional
	public AccountDTO create(Integer accountType) {
		try {
			String accountNumber = generateUniqueAccountNumber();
			AccountType accountTypeEntity = fetchAccountType(accountType);

			Account account = new Account();
			account.setAccountNumber(accountNumber);
			account.setAccountType(accountTypeEntity);
			account.setStatus(AccountStatus.ACTIVE.toString());

			Account savedAccount = accountRepository.save(account);

			return modelMapper.map(savedAccount, AccountDTO.class);
		} catch (AccountTypeInvalidException e) {
			// Rethrow the custom exception
			throw e;
		} catch (AccountGenerateAccNumberException e) {
			throw e;
		} catch (AccountTypeGetException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("An unexpected error occurred while creating the account : {}", e.getMessage(), e);
			throw new RuntimeException("Failed to create account. Please try again later.");
		}
	}

	private String generateUniqueAccountNumber() {
		String accountNumber;
		try {
			do {
				accountNumber = AccountNumberGenerator.generateRandomAccountNumber();
			} while (accountRepository.existsByAccountNumber(accountNumber));
			return accountNumber;
		} catch (Exception e) {
			LOGGER.error("An unexpected error occurred while generating the account number : {}", e.getMessage(), e);
			throw new AccountGenerateAccNumberException("Failed to generate the account number.");
		}
	}

	private AccountType fetchAccountType(Integer accountTypeId) {
		try {
			return accountTypeRepository.findById(accountTypeId)
					.orElseThrow(() -> new AccountTypeInvalidException("Invalid account type."));
		} catch (Exception e) {
			LOGGER.error("An unexpected error occurred while getting the account type : {}", e.getMessage(), e);
			throw new AccountTypeGetException("Failed to get the account type.");
		}
	}

	@Override
	@Transactional
	public void depositCash(AccountDTO accountDTO) {
		try {
			double depositAmount = accountDTO.getDepositAmount();
			String accountNumber = accountDTO.getAccountNumber();

			if (depositAmount <= 0) {
				throw new AccountInvalidDepositException("Deposit amount must be greater than zero.");
			}

			Account account = accountRepository.findByAccountNumber(accountNumber);
			if (account == null) {
				throw new AccountNotFoundException("Account not found.");
			}

			// Perform the deposit
			double currentBalance = account.getBalance();
			double newBalance = currentBalance + depositAmount;
			account.setBalance(newBalance);

			accountRepository.save(account);
		} catch (Exception e) {
			LOGGER.error("An unexpected error occurred while getting the account type : {}", e.getMessage(), e);
			throw new AccountDepositException("Failed to deposit cash. Please try again later.");
		}
	}

	@Override
	@Transactional
	public void withdrawCash(AccountDTO accountDTO) {

		try {
			double withdrawalAmount = accountDTO.getWithdrawalAmount();
			String accountNumber = accountDTO.getAccountNumber();

			if (withdrawalAmount <= 0) {
				throw new AccountInvalidWithdrawalException("Withdrawal amount must be greater than zero.");
			}

			Account account = accountRepository.findByAccountNumber(accountNumber);
			if (account == null) {
				throw new AccountNotFoundException("Account not found.");
			}

			double currentBalance = account.getBalance();
			if (withdrawalAmount > currentBalance) {
				throw new AccountInsufficientBalanceException("Insufficient balance for withdrawal.");
			}

			// Perform the withdrawal
			double newBalance = currentBalance - withdrawalAmount;
			account.setBalance(newBalance);

			accountRepository.save(account);
		} catch (Exception e) {
			LOGGER.error("An unexpected error occurred while cash withdrawal : {}", e.getMessage(), e);
			throw new AccountWithdrawalException("Failed to withdraw cash. Please try again later.");
		}

	}

	@Override
	@Transactional
	public void close(String accountNumber) {
		try {
			Account account = accountRepository.findByAccountNumber(accountNumber);
			if (account == null) {
				throw new AccountNotFoundException("Account not found.");
			}

			account.setStatus(AccountStatus.CLOSED.toString());

			accountRepository.save(account);
		} catch (Exception e) {
			LOGGER.error("An unexpected error occurred while closing account : {}", e.getMessage(), e);
			throw new AccountCloseException("Failed to close account. Please try again later.");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public AccountDTO inquire(String accountNumber) {
		try {
			Account account = accountRepository.findByAccountNumber(accountNumber);
			if (account == null) {
				throw new AccountNotFoundException("Account not found.");
			}

			return modelMapper.map(account, AccountDTO.class);
		} catch (Exception e) {
			LOGGER.error("An unexpected error occurred while inquire account : {}", e.getMessage(), e);
			throw new AccountInquireException("Failed to inquire account. Please try again later.");
		}
	}

}
