-- 1. Create a Unit of Measure (UOM)
INSERT INTO uom_data (name) VALUES ('Box');

-- 2. View all UOMs
SELECT * FROM uom_data;

-- 3. Create a Product (Make sure the uom_id matches an existing UOM)
INSERT INTO product_data (name, description, uom_id, uom_name)
VALUES ('Apple', 'Fresh Red Apple', 1, 'Box');

-- 4. View all Products
SELECT * FROM product_data;

-- 5. Delete a Product (Change the ID as needed)
DELETE FROM product_data WHERE id = 1;

-- 6. Update a Product
UPDATE product_data
SET description = 'Very Fresh Red Apple'
WHERE id = 1;

