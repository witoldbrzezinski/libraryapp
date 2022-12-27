CREATE TABLE borrows (
    id bigserial PRIMARY KEY,
    book_id BIGINT,
    customer_id BIGINT,
    return_date DATE,
    is_deleted boolean NOT NULL DEFAULT false,
    uuid VARCHAR(36) NOT NULL,
    version BIGINT DEFAULT 0
);