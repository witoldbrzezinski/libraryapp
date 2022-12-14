CREATE TABLE books (
    id bigserial PRIMARY KEY,
    isbn VARCHAR (25) NOT NULL,
    title VARCHAR (255) NOT NULL,
    author VARCHAR (150) NOT NULL,
    genre VARCHAR (50) NOT NULL,
    quantity INTEGER NOT NULL,
    CONSTRAINT quantity_nonnegative CHECK (quantity >=0),
    is_deleted boolean NOT NULL DEFAULT false,
    uuid VARCHAR(36) NOT NULL,
    version BIGINT DEFAULT 0
 );

