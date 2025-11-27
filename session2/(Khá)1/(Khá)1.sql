create schema library;
create table library.books(
    book_id serial PRIMARY KEY,
    title varchar(100) NOT NULL,
    author varchar(50) NOT NULL,
    published_year int,
    price DECIMAL
);