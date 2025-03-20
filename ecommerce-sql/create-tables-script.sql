-- Users table
CREATE TABLE Users(
	user_id serial PRIMARY KEY,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	username varchar(50) NOT NULL UNIQUE,
	email varchar(100) NOT NULL UNIQUE,
	phone_number varchar(20) NOT NULL UNIQUE,
	password TEXT NOT NULL,
	role varchar(6) DEFAULT 'USER' CHECK (role IN('USER', 'ADMIN'))
);

-- Addresses table
CREATE TABLE 	Addresses(
	address_id serial PRIMARY KEY,
	country varchar(15) NOT NULL,
	street varchar(150) NOT NULL,
	zip_code varchar(10) NOT NULL,
	instructions TEXT,
	user_id int NOT NULL REFERENCES Users ON DELETE CASCADE
);

-- Prducts table
CREATE TABLE Products(
	product_id serial PRIMARY KEY,
	name varchar(50) NOT NULL,
	description text NOT NULL,
	price DECIMAL(10, 2) NOT NULL CHECK (price > 0),
	stock int NOT NULL CHECK (stock >= 0)
);

-- Cart Items table
CREATE TABLE CartItems(
	cart_item_id serial PRIMARY KEY,
	user_id int NOT NULL REFERENCES Users ON DELETE CASCADE,
	product_id int NOT NULL REFERENCES Products ON DELETE CASCADE,
	quantity int NOT NULL CHECK (quantity > 0 ),
	UNIQUE(user_id, product_id)
);

-- Orders table
CREATE TABLE Orders(
	order_id serial PRIMARY KEY,
	user_id int NOT NULL REFERENCES Users ON DELETE CASCADE,
	total_price decimal(10, 2) NOT NULL CHECK (total_price >= 0),
	status varchar(10) DEFAULT 'PENDING' CHECK (status IN('PENDING', 'SHIPPED', 'DELIVERED', 'CANCELED')),
	created_at timestamp DEFAULT CURRENT_TIMESTAMP
);

-- Order Item table
CREATE TABLE OrderItems(
	order_item_id serial PRIMARY KEY,
	order_id int NOT NULL REFERENCES Orders ON DELETE CASCADE,
	product_id int NOT NULL REFERENCES Products ON DELETE CASCADE,
	quantity int NOT NULL CHECK (quantity > 0), 
	price decimal(10, 2) NOT NULL CHECK (price >= 0),
	UNIQUE(order_id, product_id)
);