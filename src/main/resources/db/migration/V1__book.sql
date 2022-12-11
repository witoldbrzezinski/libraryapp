CREATE TABLE books (
    id bigserial PRIMARY KEY,
    isbn VARCHAR (20) UNIQUE NOT NULL,
    title VARCHAR (255) NOT NULL,
    author VARCHAR (150) NOT NULL,
    genre VARCHAR (50) NOT NULL,
    is_active boolean NOT NULL DEFAULT false,
    uuid VARCHAR(36) NOT NULL,
    version BIGINT DEFAULT 1
 );

