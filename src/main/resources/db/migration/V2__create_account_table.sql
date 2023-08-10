-- Create table for account types
CREATE TABLE account_type (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Insert initial account types
INSERT INTO account_type (id, name) VALUES
    (1, 'Savings'),
    (2, 'Current'),
    (3, 'Credit');

-- Create sequence for account ID
CREATE SEQUENCE account_id_seq START 100000;

-- Create table for accounts
CREATE TABLE account (
    id BIGINT DEFAULT nextval('account_id_seq') PRIMARY KEY,
    accountNumber VARCHAR(20),
    accountTypeId INT,
    balance NUMERIC,
    status VARCHAR(50),
    customer_id BIGINT UNIQUE REFERENCES customer(id) ON DELETE CASCADE, -- Apply CASCADE option
    FOREIGN KEY (accountTypeId) REFERENCES account_type(id), -- Account to AccountType relationship
    CONSTRAINT FK_account_customer FOREIGN KEY (customer_id) REFERENCES customer(id) -- Account to Customer relationship
);

-- Create index on frequently used columns
CREATE INDEX idx_account_accountNumber ON account (accountNumber);
CREATE INDEX idx_account_status ON account (status);
CREATE INDEX idx_account_accountTypeId ON account (accountTypeId);