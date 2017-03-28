CREATE DATABASE netstore;
USE netstore;
CREATE TABLE categorys(
	id VARCHAR(100) PRIMARY KEY,
	name VARCHAR(100) NOT NULL UNIQUE,
	description VARCHAR(255)
);
CREATE TABLE books(
	id VARCHAR(100) PRIMARY KEY,
	name VARCHAR(100) NOT NULL UNIQUE,
	author VARCHAR(100),
	price FLOAT(8,2),
	path VARCHAR(100),
	photoFileName VARCHAR(100),
	description VARCHAR(255),
	categoryId VARCHAR(100),
	CONSTRAINT category_id_fk FOREIGN KEY(categoryId) REFERENCES categorys(ID)
);
CREATE TABLE customers(
	id VARCHAR(100) PRIMARY KEY,
	username VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(100) NOT NULL,
	phone VARCHAR(100) NOT NULL,
	address VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL,
	actived BIT(1),
	code VARCHAR(100) NOT NULL UNIQUE
);
CREATE TABLE orders(
	id VARCHAR(100) PRIMARY KEY,
	orderNum VARCHAR(100) NOT NULL UNIQUE,
	quantity INT ,
	amount FLOAT(10,2),
	status INT,
	customersId VARCHAR(100),
	CONSTRAINT customers_id_fk FOREIGN KEY(customersId) REFERENCES customers(ID)
);
CREATE TABLE orders_item(
	id VARCHAR(100) PRIMARY KEY,
	quantity INT ,
	price FLOAT(10,2),
	booksId VARCHAR(100),
	ordersId VARCHAR(100),
	CONSTRAINT books_id_fk FOREIGN KEY(booksId) REFERENCES books(ID),
	CONSTRAINT orders_id_fk FOREIGN KEY(ordersId) REFERENCES orders(ID)
);
# permission tables
CREATE TABLE users(
	id INT PRIMARY KEY,
	username VARCHAR(100) NOT NULL UNIQUE,
	nickname VARCHAR(100),
	password VARCHAR(100) NOT NULL
);
CREATE TABLE roles(
	id INT PRIMARY KEY,
	name VARCHAR(100) NOT NULL UNIQUE,
	description VARCHAR(255)
);
CREATE TABLE functions(
	id INT PRIMARY KEY,
	name VARCHAR(100) NOT NULL UNIQUE,
	uri VARCHAR(255) NOT NULL
);
CREATE TABLE users_roles(
	u_id INT,
	r_id INT,
	PRIMARY KEY(u_id,r_id),
	CONSTRAINT user_id_fk FOREIGN KEY(u_id) REFERENCES users(ID),
	CONSTRAINT roles_id_fk FOREIGN KEY(r_id) REFERENCES roles(ID)
);
CREATE TABLE roles_functions(
	r_id INT,
	f_id INT,
	PRIMARY KEY(f_id,r_id),
	CONSTRAINT functions_id_fk FOREIGN KEY(f_id) REFERENCES functions(ID),
	CONSTRAINT roles_id_fk1 FOREIGN KEY(r_id) REFERENCES roles(ID)
);