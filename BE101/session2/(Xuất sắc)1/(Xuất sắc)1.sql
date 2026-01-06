create schema hotel;

create table hotel.RoomTypes(
    room_type_id serial PRIMARY KEY ,
    type_name varchar(50) NOT NULL UNIQUE ,
    price_per_night numeric(10,2) CHECK(price_per_night > 0),
    max_capacity int CHECK ( max_capacity > 0 )
);

create table hotel.Rooms(
    room_id serial PRIMARY KEY ,
    room_number varchar(10) NOT NULL UNIQUE ,
    room_type_id int references hotel.RoomTypes(room_type_id),
    status varchar(20) CHECK(status in ('Available','Occupied','Maintenance'))
);

create table hotel.Customers(
    customer_id serial PRIMARY KEY ,
    full_name varchar(100) NOT NULL ,
    email varchar(100) UNIQUE NOT NULL ,
    phone varchar(15) NOT NULL
);

create table hotel.Bookings(
    booking_id serial PRIMARY KEY ,
    customer_id int references hotel.Customers(customer_id),
    room_id int references hotel.Rooms(room_id),
    check_in date NOT NULL ,
    check_out date NOT NULL ,
    status varchar(20) check(status in ('Pending','Confirmed','Cancelled'))
);

create table hotel.Payments(
    payment_id serial PRIMARY KEY ,
    booking_id int references hotel.Bookings(booking_id),
    amount numeric(10,2) check(amount >= 0),
    payment_date date NOT NULL ,
    method varchar(20) check(method in ('Card','Cash','Bank Transfer'))
);