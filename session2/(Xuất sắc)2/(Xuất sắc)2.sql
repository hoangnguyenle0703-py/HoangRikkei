create schema shop;

create table shop.Users(
    user_id serial PRIMARY KEY ,
    username varchar(50) UNIQUE NOT NULL ,
    email varchar(100) UNIQUE NOT NULL ,
    password varchar(100) NOT NULL ,
    role varchar(20) check(role in ('Customer','Admin'))
);

create table shop.Categories(
    category_id serial PRIMARY KEY ,
    category_name varchar(100) UNIQUE NOT NULL
);

create table shop.Products(
    product_id serial PRIMARY KEY ,
    product_name varchar(100) NOT NULL ,
    price numeric(10,2) check(price > 0),
    stock int check(stock >= 0),
    category_id int references shop.Categories(category_id)
);

create table shop.Orders(
    order_id serial PRIMARY KEY ,
    user_id int references shop.Users(user_id),
    order_date date NOT NULL ,
    status varchar(20) check(status in ('Pending','Shipped','Delivered','Cancelled'))
);

create table shop.OrderDetails(
    order_detail_id serial PRIMARY KEY ,
    order_id int references shop.Orders(order_id),
    product_id int references shop.Products(product_id),
    quantity int check(quantity > 0),
    price_each numeric(10,2) check(price_each > 0)
);

create table shop.Payments(
    payment_id serial PRIMARY KEY ,
    order_id int references shop.Orders(order_id),
    amount numeric(10,2) check(amount >= 0),
    payment_date date NOT NULL ,
    method varchar(30) check(method in ('Credit','Card','Momo','Bank Transfer','Cash'))
);