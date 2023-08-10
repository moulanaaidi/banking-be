CREATE SEQUENCE customer_id_seq START 100000;

CREATE TABLE customer (
    id BIGINT DEFAULT nextval('customer_id_seq') PRIMARY KEY,
    name VARCHAR(255)
);