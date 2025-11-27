create schema sales;

create table sales.Customers(
    customer_id serial PRIMARY KEY ,
    first_name varchar(50) NOT NULL ,
    last_name varchar(50) NOT NULL ,
    email varchar(50) UNIQUE NOT NULL ,
    phone varchar(11)
);

create table sales.Products(
    product_id serial PRIMARY KEY ,
    product_name varchar(100) NOT NULL ,
    price DECIMAL NOT NULL ,
    stock_quantity int NOT NULL
);

create table sales.Orders(
    order_id serial PRIMARY KEY ,
    customer_id serial references sales.Customers(customer_id),
    order_date date
);

create table sales.OrderItems(
    order_item_id serial PRIMARY KEY ,
    order_id serial references sales.Orders(order_id),
    product_id serial references sales.Products(product_id),
    quantity int check(quantity > 0)
);