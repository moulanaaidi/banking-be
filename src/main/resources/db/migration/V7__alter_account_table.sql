-- Alter table to add unique constraint on accountNumber column
ALTER TABLE account
ADD CONSTRAINT uq_account_accountNumber UNIQUE (accountNumber);
