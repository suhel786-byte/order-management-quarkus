-- 100 Customers

INSERT INTO customers (
    name,
    email,
    phone,
    created_at,
    updated_at
)
SELECT
    'Customer ' || g,
    'customer' || g || '@gmail.com',
    '98765' || LPAD(g::text, 5, '0'),
    NOW(),
    NOW()
FROM generate_series(1,100) g;

INSERT INTO orders (
    customer_id,
    order_number,
    amount,
    status,
    created_at,
    updated_at
)
SELECT
    (
        SELECT id
        FROM customers
        ORDER BY RANDOM()
        LIMIT 1
    ),
    'ORD-' || LPAD(g::text, 6, '0'),
    ROUND((RANDOM() * 50000)::numeric, 2),
    (
        ARRAY[
            'PENDING',
            'PROCESSING',
            'SHIPPED',
            'DELIVERED',
            'CANCELLED'
        ]
    )[FLOOR(RANDOM() * 5 + 1)],
    NOW(),
    NOW()
FROM generate_series(1,500) g;