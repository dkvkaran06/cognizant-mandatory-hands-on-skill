SET SERVEROUTPUT ON;

CREATE OR REPLACE PACKAGE CustomerManagement AS
    PROCEDURE AddCustomer(
        p_CustomerID NUMBER,
        p_Name VARCHAR2,
        p_DOB DATE,
        p_Balance NUMBER
    );

    PROCEDURE UpdateCustomer(
        p_CustomerID NUMBER,
        p_Name VARCHAR2
    );

    FUNCTION GetCustomerBalance(
        p_CustomerID NUMBER
    ) RETURN NUMBER;
END CustomerManagement;
/

CREATE OR REPLACE PACKAGE BODY CustomerManagement AS

    PROCEDURE AddCustomer(
        p_CustomerID NUMBER,
        p_Name VARCHAR2,
        p_DOB DATE,
        p_Balance NUMBER
    )
    IS
    BEGIN
        INSERT INTO Customers
        (CustomerID, Name, DOB, Balance, LastModified, IsVIP)
        VALUES
        (p_CustomerID, p_Name, p_DOB, p_Balance, SYSDATE, 'FALSE');
    END;

    PROCEDURE UpdateCustomer(
        p_CustomerID NUMBER,
        p_Name VARCHAR2
    )
    IS
    BEGIN
        UPDATE Customers
        SET Name = p_Name
        WHERE CustomerID = p_CustomerID;
    END;

    FUNCTION GetCustomerBalance(
        p_CustomerID NUMBER
    )
    RETURN NUMBER
    IS
        v_Balance NUMBER;
    BEGIN
        SELECT Balance
        INTO v_Balance
        FROM Customers
        WHERE CustomerID = p_CustomerID;

        RETURN v_Balance;
    END;

END CustomerManagement;
/

BEGIN
    CustomerManagement.AddCustomer(5,'Chris',TO_DATE('1997-08-10','YYYY-MM-DD'),9000);
END;
/

BEGIN
    CustomerManagement.UpdateCustomer(5,'Chris Evans');
END;
/

SELECT CustomerManagement.GetCustomerBalance(5) AS Balance
FROM Dual;

CREATE OR REPLACE PACKAGE EmployeeManagement AS

    PROCEDURE HireEmployee(
        p_EmployeeID NUMBER,
        p_Name VARCHAR2,
        p_Position VARCHAR2,
        p_Salary NUMBER,
        p_Department VARCHAR2,
        p_HireDate DATE
    );

    PROCEDURE UpdateEmployee(
        p_EmployeeID NUMBER,
        p_Salary NUMBER
    );

    FUNCTION AnnualSalary(
        p_EmployeeID NUMBER
    ) RETURN NUMBER;

END EmployeeManagement;
/

CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS

    PROCEDURE HireEmployee(
        p_EmployeeID NUMBER,
        p_Name VARCHAR2,
        p_Position VARCHAR2,
        p_Salary NUMBER,
        p_Department VARCHAR2,
        p_HireDate DATE
    )
    IS
    BEGIN
        INSERT INTO Employees
        VALUES
        (
            p_EmployeeID,
            p_Name,
            p_Position,
            p_Salary,
            p_Department,
            p_HireDate
        );
    END;

    PROCEDURE UpdateEmployee(
        p_EmployeeID NUMBER,
        p_Salary NUMBER
    )
    IS
    BEGIN
        UPDATE Employees
        SET Salary = p_Salary
        WHERE EmployeeID = p_EmployeeID;
    END;

    FUNCTION AnnualSalary(
        p_EmployeeID NUMBER
    )
    RETURN NUMBER
    IS
        v_Salary NUMBER;
    BEGIN
        SELECT Salary
        INTO v_Salary
        FROM Employees
        WHERE EmployeeID = p_EmployeeID;

        RETURN v_Salary * 12;
    END;

END EmployeeManagement;
/

BEGIN
    EmployeeManagement.HireEmployee(
        3,
        'David',
        'Analyst',
        50000,
        'Finance',
        SYSDATE
    );
END;
/

BEGIN
    EmployeeManagement.UpdateEmployee(3,55000);
END;
/

SELECT EmployeeManagement.AnnualSalary(3) AS Annual_Salary
FROM Dual;

CREATE OR REPLACE PACKAGE AccountOperations AS

    PROCEDURE OpenAccount(
        p_AccountID NUMBER,
        p_CustomerID NUMBER,
        p_AccountType VARCHAR2,
        p_Balance NUMBER
    );

    PROCEDURE CloseAccount(
        p_AccountID NUMBER
    );

    FUNCTION GetTotalBalance(
        p_CustomerID NUMBER
    ) RETURN NUMBER;

END AccountOperations;
/

CREATE OR REPLACE PACKAGE BODY AccountOperations AS

    PROCEDURE OpenAccount(
        p_AccountID NUMBER,
        p_CustomerID NUMBER,
        p_AccountType VARCHAR2,
        p_Balance NUMBER
    )
    IS
    BEGIN
        INSERT INTO Accounts
        VALUES
        (
            p_AccountID,
            p_CustomerID,
            p_AccountType,
            p_Balance,
            SYSDATE
        );
    END;

    PROCEDURE CloseAccount(
        p_AccountID NUMBER
    )
    IS
    BEGIN
        DELETE FROM Accounts
        WHERE AccountID = p_AccountID;
    END;

    FUNCTION GetTotalBalance(
        p_CustomerID NUMBER
    )
    RETURN NUMBER
    IS
        v_Total NUMBER;
    BEGIN
        SELECT SUM(Balance)
        INTO v_Total
        FROM Accounts
        WHERE CustomerID = p_CustomerID;

        RETURN NVL(v_Total,0);
    END;

END AccountOperations;
/

BEGIN
    AccountOperations.OpenAccount(3,1,'Savings',5000);
END;
/

BEGIN
    AccountOperations.CloseAccount(3);
END;
/

SELECT AccountOperations.GetTotalBalance(1) AS Total_Balance
FROM Dual;
