CREATE DATABASE PhoneStoreManagement;

CREATE TABLE Admin(
    ID SERIAL PRIMARY KEY ,
    username VARCHAR(50) NOT NULL UNIQUE ,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE Product(
    ID SERIAL PRIMARY KEY ,
    name VARCHAR(100) NOT NULL ,
    brand VARCHAR(50) NOT NULL ,
    price DECIMAL(12,2) NOT NULL ,
    stock INT NOT NULL
);

CREATE TABLE Customer(
    ID SERIAL PRIMARY KEY ,
    name VARCHAR(100) NOT NULL ,
    phone VARCHAR(20) ,
    email VARCHAR(100) UNIQUE ,
    address VARCHAR(255)
);

CREATE TABLE Invoice(
    ID SERIAL PRIMARY KEY ,
    customer_id INT REFERENCES Customer(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(12,2) NOT NULL
);

CREATE TABLE Invoice_details(
    ID SERIAL PRIMARY KEY ,
    invoice_id INT REFERENCES Invoice(id),
    product_id INT REFERENCES Product(id),
    quantity INT NOT NULL ,
    unit_price DECIMAL(12,2) NOT NULL
);

INSERT INTO Admin(username, password)  VALUES ('admin','admin123');