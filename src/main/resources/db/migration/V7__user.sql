CREATE TABLE roles (
    id bigserial PRIMARY KEY,
    username VARCHAR (150) NOT NULL UNIQUE,
    password VARCHAR (120) NOT NULL,
    email VARCHAR (150) NOT NULL,
    is_deleted boolean NOT NULL DEFAULT false,
    uuid VARCHAR(36) NOT NULL,
    version BIGINT DEFAULT 0
 );

