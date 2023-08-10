-- Drop the existing foreign key constraint
ALTER TABLE account DROP CONSTRAINT FK_account_customer;

-- Alter the customer_id column to allow multiple accounts for a customer
ALTER TABLE account
DROP CONSTRAINT IF EXISTS uq_account_customer_id;

-- Add a new foreign key constraint
ALTER TABLE account
ADD CONSTRAINT FK_account_customer
FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE; -- Apply CASCADE option