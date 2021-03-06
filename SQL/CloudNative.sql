-- Cloud Native Game Store Schema

-- Level Up
create schema if not exists level_up;
use level_up;

create table if not exists level_up (
    level_up_id int(11) not null auto_increment primary key,
    customer_id int(11) not null,
    points int(11) not null,
    member_date date not null
);

-- Invoice
create schema if not exists invoice;
use invoice;

create table if not exists invoice (
    invoice_id int(11) not null auto_increment primary key,
    customer_id int(11) not null,
    purchase_date date not null
);

create table if not exists invoice_item (
    invoice_item_id int(11) not null auto_increment primary key,
    invoice_id int(11) not null,
    inventory_id int(11) not null,
    quantity int(11) not null,
    unit_price decimal(7,2) not null    
);

alter table invoice_item add constraint fk_invoice_item_invoice foreign key (invoice_id) references invoice(invoice_id);

-- Inventory
create schema if not exists inventory;
use inventory;

create table if not exists inventory (
    inventory_id int(11) not null auto_increment primary key,
    product_id int(11) not null,
    quantity int(11) not null
);

-- Product
create schema if not exists product;
use product;

create table if not exists product (
    product_id int(11) not null auto_increment primary key,
    product_name varchar(50) not null,
    product_description varchar(255) not null,
    list_price decimal(7,2) not null,
    unit_cost decimal(7,2) not null
);

-- Customer
create schema if not exists customer;
use customer;

create table if not exists customer (
    customer_id int(11) not null auto_increment primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    street varchar(50) not null,
    city varchar(50) not null,
    zip varchar(10) not null,
    email varchar(75) not null,
    phone varchar(20) not null
);

-- Security
create schema if not exists spring_security;
use spring_security;

create table if not exists users(
    username varchar(50) not null primary key,
    password varchar(100) not null,
    enabled boolean not null
);

create table if not exists authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username));
    create unique index ix_auth_username on authorities (username,authority
);

 
 










 
