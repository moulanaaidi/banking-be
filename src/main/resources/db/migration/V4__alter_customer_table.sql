-- Alter table to add document columns
ALTER TABLE customer
ADD COLUMN document_number VARCHAR(50),
ADD COLUMN document_type_id INT;

-- Add foreign key constraint to document_type table
ALTER TABLE customer
ADD CONSTRAINT fk_document_type
FOREIGN KEY (document_type_id) REFERENCES document_type(id);