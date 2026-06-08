ALTER TABLE orders
    ADD COLUMN product_name VARCHAR(255);

UPDATE orders
SET product_name =
        (
         ARRAY[
         'MacBook Pro',
         'Dell Latitude',
         'Samsung Monitor',
         'Office Chair',
         'iPhone 16',
         'Lenovo ThinkPad',
         'Wireless Mouse',
         'Mechanical Keyboard',
         'Gaming Laptop',
         'USB Hub'
             ]
            )[FLOOR(RANDOM() * 10 + 1)];