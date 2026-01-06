CREATE TABLE accounts(
    account_id SERIAL PRIMARY KEY ,
    account_name VARCHAR(100) NOT NULL ,
    balance NUMERIC(12,2) NOT NULL
);

INSERT INTO accounts(account_name, balance) VALUES ('Hoang',1000000.00);

BEGIN ;
SELECT accounts.balance FROM accounts WHERE account_id = 1;
SELECT accounts.balance FROM accounts WHERE account_id = 1;
COMMIT ;

BEGIN ;
UPDATE accounts SET balance = balance * 10 WHERE account_id = 1;
COMMIT ;