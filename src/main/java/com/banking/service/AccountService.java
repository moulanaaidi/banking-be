package com.banking.service;

import com.banking.dto.AccountDTO;

public interface AccountService {

	AccountDTO create(Integer accountType);

	void depositCash(AccountDTO accountDTO);

	void withdrawCash(AccountDTO accountDTO);

	void close(String accountNumber);

	AccountDTO inquire(String accountNumber);

}
