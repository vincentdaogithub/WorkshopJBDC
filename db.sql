USE master;
GO

IF EXISTS (SELECT * FROM sys.databases WHERE name = 'WorkshopJDBC')
    DROP DATABASE WorkshopJDBC;
GO

CREATE DATABASE WorkshopJDBC;
GO

USE WorkshopJDBC;
GO

CREATE TABLE tbl_Mobile (
    mobileID VARCHAR(10) PRIMARY KEY,
    description VARCHAR(250) NOT NULL,
    price FLOAT,
    mobileName VARCHAR(20) NOT NULL,
    yearOfProduction INT,
    quantity INT,
    notSale BIT     -- 0: sale, 1: not sale
);
GO

CREATE TABLE tbl_User (
    userID VARCHAR(20) PRIMARY KEY,
    password INT NOT NULL,
    fullName VARCHAR(50) NOT NULL,
    role INT    -- 0: user, 1: manager, 2: staff
);
GO

CREATE TABLE tbl_Cart (
    cartID VARCHAR(20) PRIMARY KEY,
    userID VARCHAR(20) FOREIGN KEY REFERENCES tbl_User(userID) ON DELETE CASCADE
);
GO

CREATE TABLE tbl_CartDetail (
    cartID VARCHAR(20) FOREIGN KEY REFERENCES tbl_Cart(cartID) ON DELETE CASCADE,
    mobileID VARCHAR(10) FOREIGN KEY REFERENCES tbl_Mobile(mobileID) ON DELETE CASCADE,
    quantity INT NOT NULL,
    CONSTRAINT UC_CartDetail UNIQUE(cartID, mobileID)
)

INSERT INTO tbl_Mobile
VALUES
    ('M0000', 'First phone ever', 10.0, 'Nokia 3310', 2000, 10, 0),
    ('M0001', 'Second best phone', 25.0, 'Iphone 12', 2020, 5, 0),
    ('M0002', 'Third time''s a charm', 45.0, 'Iphone 14', 2022, 30, 1);
GO

INSERT INTO tbl_User
VALUES
    ('John Doe', 12345, 'John Doe', 0),
    ('Jane Doe', 12345, 'Jane Doe', 2);
GO

INSERT INTO tbl_Cart
VALUES
    ('C0000', 'John Doe');
GO

INSERT INTO tbl_CartDetail
VALUES
    ('C0000', 'M0000', 5),
    ('C0000', 'M0002', 10);
GO