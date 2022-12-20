CREATE TABLE customers (
    id bigserial PRIMARY KEY,
    first_name VARCHAR (100) NOT NULL,
    last_name VARCHAR (100) NOT NULL,
    gender VARCHAR (10) NOT NULL,
    birth_date DATE NOT NULL,
    personal_number VARCHAR (11) UNIQUE NOT NULL,
    is_deleted boolean DEFAULT false,
    uuid VARCHAR(36) UNIQUE NOT NULL,
    version BIGINT DEFAULT 0
);