CREATE TABLE users(
    user_id SERIAL PRIMARY KEY ,
    email VARCHAR(100),
    username VARCHAR(50)
);

CREATE INDEX idx_email_hash ON users USING hash(email);

EXPLAIN ANALYSE
SELECT * FROM users WHERE email = 'example@example.com';