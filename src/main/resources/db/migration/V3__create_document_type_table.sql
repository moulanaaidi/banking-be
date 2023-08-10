-- Create table for document types
CREATE TABLE document_type (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Insert common document types
INSERT INTO document_type (id, name)
VALUES
    (1, 'Identification Number'),
    (2, 'Passport');