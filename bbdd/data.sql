-- Tablas para sistema e-commerce en MySQL

CREATE DATABASE IF NOT EXISTS online_shop;

USE online_shop;

CREATE TABLE IF NOT EXISTS online_shop.customer (
    id bigint NOT NULL AUTO_INCREMENT,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    surname varchar(255) NOT NULL,
    surname2 varchar(255) NOT NULL,
    address text NOT NULL,
    province varchar(255) NOT NULL,
    region varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    phone varchar(50),
    status boolean NOT NULL,
    rol varchar(50),
    PRIMARY KEY (id),
    UNIQUE KEY (username),
    UNIQUE KEY (email)
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS online_shop.product (
    id bigint NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    description text NOT NULL,
    stock int NOT NULL,
    type varchar(100) NOT NULL,
    price double NOT NULL,
    image text NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS online_shop.cart (
    id bigint NOT NULL AUTO_INCREMENT,
    customer_id bigint NOT NULL,
    date date NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS online_shop.cart_details (
    id bigint NOT NULL AUTO_INCREMENT,
    cart_id bigint NOT NULL,
    product_id bigint NOT NULL,
    price double NOT NULL,
    quantity int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    UNIQUE KEY (cart_id, product_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS online_shop.purchase (
    id bigint NOT NULL AUTO_INCREMENT,
    customer_id bigint NOT NULL,
    purchase_date date NOT NULL,
    total_amount double NOT NULL,
    status varchar(50) NOT NULL,
    shipping_address text NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS online_shop.purchase_details (
    id bigint NOT NULL AUTO_INCREMENT,
    purchase_id bigint NOT NULL,
    product_id bigint NOT NULL,
    price double NOT NULL,
    quantity int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (purchase_id) REFERENCES purchase(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id)
) ENGINE=InnoDB;