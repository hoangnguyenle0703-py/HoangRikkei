INSERT INTO accounts(account_name, balance)
VALUES ('Nguyễn Văn A', 20000.00);

BEGIN ;
SELECT accounts.balance FROM accounts WHERE account_id = 2;
UPDATE accounts SET balance = balance-20000 WHERE account_id = 2;
UPDATE accounts SET balance = balance + 20000 WHERE account_id = 1;
COMMIT ;

BEGIN ;
SELECT accounts.balance FROM accounts WHERE account_id = 2;
UPDATE accounts SET balance = balance - 20000 WHERE account_id = 2;
UPDATE accounts SET balance = balance + 20000 WHERE account_id = 1;
ROLLBACK ;