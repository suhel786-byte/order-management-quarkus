CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

INSERT INTO users (
    username,
    password,
    role,
    created_at,
    updated_at
)
VALUES (
           'admin',
           'admin123',
           'ADMIN',
           NOW(),
           NOW()
       );