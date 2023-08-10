-- Alter table to make documentNumber unique
ALTER TABLE customer
ADD CONSTRAINT uq_document_number UNIQUE (document_number);

-- Drop the existing foreign key constraint
ALTER TABLE customer
DROP CONSTRAINT fk_document_type;

-- Add foreign key constraint to document_type table with the desired name
ALTER TABLE customer
ADD CONSTRAINT fk_document_type
FOREIGN KEY (document_type_id) REFERENCES document_type(id);
