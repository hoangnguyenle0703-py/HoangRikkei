create schema library;

create table library.Books(
    book_id int PRIMARY KEY ,
    title varchar(100),
    author varchar(100),
    published_year int,
    available boolean DEFAULT (True)
);

create table library.Members(
    member_id int PRIMARY KEY ,
    name varchar(100),
    email varchar(100) UNIQUE ,
    join_date date default (current_date)
);

create SCHEMA Sales;

create table Sales.Products(
    product_id serial PRIMARY KEY ,
    product_name varchar(100),
    price numeric(10,2),
    stock_quantity int
);

create table Sales.Orders(
    order_id serial PRIMARY KEY ,
    order_date date default (current_date),
    member_id int references library.Members(member_id)
);

create table Sales.OrderDetails(
    order_detail_id serial PRIMARY KEY ,
    order_id int references Sales.Orders(order_id),
    product_id int references Sales.Products(product_id),
    quantity int
);

ALTER table library.Books
    ADD COLUMN genre varchar(100);
ALTER table library.Books
    rename column available to is_available;

ALTER table library.Members
    drop email;

DROP table Sales.OrderDetails;