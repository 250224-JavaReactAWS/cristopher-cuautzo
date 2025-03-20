-- Users table
INSERT INTO Users (first_name, last_name, username, email, phone_number, password, role) VALUES 
	('Massimo', 'Brundell', 'mbrundell0', 'mbrundell0@timesonline.co.uk', '903-353-5574', 'wC6$ePXW(p+x', 'USER'),
	('Vernen', 'Stathor', 'vstathor1', 'vstathor1@wired.com', '819-980-7254', 'gG7@6cBu{f|/', 'USER'),
	('Putnam', 'Stroud', 'pstroud3', 'pstroud3@businessweek.com', '572-494-0674', 'rY6+8Izt', 'USER'),
	('Carrie', 'Hammersley', 'chammersley4', 'chammersley4@hp.com', '988-119-2559', 'aD7_sZ.E4NfKI', 'ADMIN'),
  ('Eloisa', 'Zollner', 'ezollner2', 'ezollner2@prweb.com', '457-154-7781', 'uK2}(fSgz', 'ADMIN');

-- Addresses table
INSERT INTO Addresses (country, street, zip_code, instructions, user_id) VALUES
	('USA', '123 Main St', '10001', 'Leave at the front door', 1),
	('Philippines', '22464 Fair Oaks Junction', '1102', 'Room 1751', 1),
	('Portugal', '52 Banding Drive', '2425-009', '2nd Floor', 2),
	('Canada', '456 Maple Rd', '20002', 'Call upon arrival', 2),
	('United Kingdom', '6 Melvin Crossing', 'LE15', 'Apt 1223', 3),
	('France', '27 Grasskamp Junction', '94865 CEDEX', '3rd Floor', 3);

-- Prducts table
INSERT INTO Products (name, description, price, stock) VALUES
		('Laptop', 'High-performance gaming laptop', 1200.00, 15),
		('Smartphone', 'Latest model with OLED display', 899.99, 30),
		('Headphones', 'Noise-canceling wireless headset', 199.99, 50),
		('Keyboard', 'Mechanical keyboard with RGB lighting', 89.99, 25),
		('Monitor', '27-inch 4K UHD display', 349.99, 10);

-- Cart Items table
INSERT INTO CartItems (user_id, product_id, quantity) VALUES
	(1, 2, 2),
	(1, 3, 1),
	(2, 1, 1),
	(2, 4, 1),
	(2, 5, 2),
	(3, 1, 5);

-- Orders table
INSERT INTO Orders (user_id, total_price, status) VALUES
	(1, 1999.97, 'PENDING'),
	(2, 1989.97, 'SHIPPED'),
	(3, 60000, 'DELIVERED');

-- Order Item table
INSERT INTO OrderItems (order_id, product_id, quantity, price) VALUES
	(1, 2, 2, 1799.98),
	(1, 3, 1, 199.99),
	(2, 1, 1, 1200),
	(2, 4, 1, 89.99),
	(2, 5, 2, 699.98),
	(3, 1, 5, 6000);
  