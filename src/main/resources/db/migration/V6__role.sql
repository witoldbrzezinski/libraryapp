CREATE TABLE roles (
    id bigserial PRIMARY KEY,
    role VARCHAR(25) NOT NULL UNIQUE
 );

INSERT INTO roles(role) VALUES('ROLE_USER');
INSERT INTO roles(role) VALUES('ROLE_ADMIN');
