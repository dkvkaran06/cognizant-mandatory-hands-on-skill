SET SERVEROUTPUT ON;
CREATE TABLE Customers (
    CustomerID NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    DOB DATE,
    Balance NUMBER,
    LastModified DATE
);
CREATE TABLE Accounts (
    AccountID NUMBER PRIMARY KEY,
    CustomerID NUMBER,
    AccountType VARCHAR2(20),
    Balance NUMBER,
    LastModified DATE,
    FOREIGN KEY(CustomerID)
    REFERENCES Customers(CustomerID)
);
CREATE TABLE Transactions (
    TransactionID NUMBER PRIMARY KEY,
    AccountID NUMBER,
    TransactionDate DATE,
    Amount NUMBER,
    TransactionType VARCHAR2(20),
    FOREIGN KEY(AccountID)
    REFERENCES Accounts(AccountID)
);
CREATE TABLE Loans (
    LoanID NUMBER PRIMARY KEY,
    CustomerID NUMBER,
    LoanAmount NUMBER,
    InterestRate NUMBER,
    StartDate DATE,
    EndDate DATE,
    FOREIGN KEY(CustomerID)
    REFERENCES Customers(CustomerID)
);
CREATE TABLE Employees (
    EmployeeID NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    Position VARCHAR2(50),
    Salary NUMBER,
    Department VARCHAR2(50),
    HireDate DATE
);
INSERT INTO Customers
VALUES(1,'John Doe',
TO_DATE('1985-05-15','YYYY-MM-DD'),
1000,
SYSDATE);

INSERT INTO Customers
VALUES(2,'Jane Smith',
TO_DATE('1990-07-20','YYYY-MM-DD'),
1500,
SYSDATE);
INSERT INTO Accounts
VALUES(1,1,'Savings',1000,SYSDATE);

INSERT INTO Accounts
VALUES(2,2,'Checking',1500,SYSDATE);
INSERT INTO Transactions
VALUES(1,1,SYSDATE,200,'Deposit');

INSERT INTO Transactions
VALUES(2,2,SYSDATE,300,'Withdrawal');
INSERT INTO Loans
VALUES(1,1,5000,5,SYSDATE,ADD_MONTHS(SYSDATE,60));
INSERT INTO Employees
VALUES(1,'Alice Johnson',
'Manager',
70000,
'HR',
TO_DATE('2015-06-15','YYYY-MM-DD'));

INSERT INTO Employees
VALUES(2,'Bob Brown',
'Developer',
60000,
'IT',
TO_DATE('2017-03-20','YYYY-MM-DD'));
COMMIT;

INSERT INTO Customers
VALUES(
3,
'Robert',
TO_DATE('1950-08-15','YYYY-MM-DD'),
12000,
SYSDATE
);

INSERT INTO Loans
VALUES(
2,
3,
8000,
8,
SYSDATE,
ADD_MONTHS(SYSDATE,36)
);

COMMIT;

SET SERVEROUTPUT ON;

DECLARE

CURSOR c1 IS
SELECT c.CustomerID,
       c.Name,
       c.DOB,
       l.InterestRate
FROM Customers c
JOIN Loans l
ON c.CustomerID=l.CustomerID;

v_age NUMBER;

BEGIN

FOR rec IN c1 LOOP

v_age:=TRUNC(MONTHS_BETWEEN(SYSDATE,rec.DOB)/12);

IF v_age>60 THEN

UPDATE Loans
SET InterestRate=InterestRate-1
WHERE CustomerID=rec.CustomerID;

DBMS_OUTPUT.PUT_LINE(
'Discount Applied to '
||rec.Name);

END IF;

END LOOP;

COMMIT;

END;
/

-- SCENARIO 2 :
ALTER TABLE Customers
ADD IsVIP VARCHAR2(5);

UPDATE Customers
SET IsVIP = 'FALSE';

COMMIT;

SET SERVEROUTPUT ON;

DECLARE
    CURSOR c2 IS
        SELECT CustomerID, Name, Balance
        FROM Customers;
BEGIN
    FOR rec IN c2 LOOP
        IF rec.Balance > 10000 THEN
            UPDATE Customers
            SET IsVIP = 'TRUE'
            WHERE CustomerID = rec.CustomerID;

            DBMS_OUTPUT.PUT_LINE(rec.Name || ' promoted to VIP');
        END IF;
    END LOOP;

    COMMIT;
END;
/

SELECT CustomerID, Name, Balance, IsVIP
FROM Customers;

--SCENARIO :3

SET SERVEROUTPUT ON;

DECLARE
    CURSOR c3 IS
        SELECT c.Name,
               l.LoanID,
               l.EndDate
        FROM Customers c
        JOIN Loans l
        ON c.CustomerID = l.CustomerID
        WHERE l.EndDate BETWEEN SYSDATE AND SYSDATE + 30;

BEGIN
    FOR rec IN c3 LOOP
        DBMS_OUTPUT.PUT_LINE(
            'Reminder: Dear ' || rec.Name ||
            ', your loan (Loan ID: ' || rec.LoanID ||
            ') is due on ' || TO_CHAR(rec.EndDate, 'DD-MON-YYYY')
        );
    END LOOP;
END;
/