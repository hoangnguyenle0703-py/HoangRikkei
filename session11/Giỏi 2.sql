CREATE TABLE accounts(
    account_id SERIAL PRIMARY KEY ,
    customer_name VARCHAR(100),
    balance NUMERIC(12,2)
);

CREATE TABLE transactions(
    trans_id SERIAL PRIMARY KEY ,
    account_id INT REFERENCES accounts(account_id),
    amount NUMERIC(12,2),
    trans_type VARCHAR(20), -- WITHDRAW OR DEPOSIT
    created_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO accounts (customer_name, balance) VALUES ('Nguyen Van An', 1000000);

BEGIN;
UPDATE accounts
SET balance = balance - 200000
WHERE account_id = 1 AND balance >= 200000;
INSERT INTO transactions (account_id, amount, trans_type)
VALUES (1, 200000, 'WITHDRAW');
COMMIT;

SELECT * FROM accounts;

BEGIN;
UPDATE accounts SET balance = balance - 500000 WHERE account_id = 1;
INSERT INTO transactions (account_id, amount, trans_type)
VALUES (999, 500000, 'WITHDRAW');
ROLLBACK;

SELECT * FROM accounts;