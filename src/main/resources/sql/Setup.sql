BEGIN;

-- Drop tables i korrekt rækkefølge pga. FK-afhængigheder
DROP TABLE IF EXISTS public.cupcakes_orderlines;
DROP TABLE IF EXISTS public.orderlines;
DROP TABLE IF EXISTS public.cupcakes;
DROP TABLE IF EXISTS public.orders;
DROP TABLE IF EXISTS public.status;
DROP TABLE IF EXISTS public.cupcake_tops;
DROP TABLE IF EXISTS public.cupcake_bottoms;
DROP TABLE IF EXISTS public.customers;
DROP TABLE IF EXISTS public.admins;

-- Admins
CREATE TABLE IF NOT EXISTS public.admins (
    admin_id serial PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL
    );

-- Customers
CREATE TABLE IF NOT EXISTS public.customers (
    customer_id serial PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    balance NUMERIC
    );

-- Status
CREATE TABLE IF NOT EXISTS public.status (
 status_id serial PRIMARY KEY,
 is_paid BOOLEAN NOT NULL DEFAULT false,
 is_picked_up BOOLEAN NOT NULL DEFAULT false
);

-- Orders
CREATE TABLE IF NOT EXISTS public.orders (
order_id serial PRIMARY KEY,
customer_id INTEGER NOT NULL,
order_date DATE NOT NULL,
total_price INTEGER NOT NULL,
status_id INTEGER NOT NULL,
CONSTRAINT fk_customer_order FOREIGN KEY (customer_id)
REFERENCES public.customers (customer_id)
ON DELETE CASCADE,
CONSTRAINT fk_status_order FOREIGN KEY (status_id)
REFERENCES public.status (status_id)
ON DELETE CASCADE
);

-- Cupcake Bottoms
CREATE TABLE IF NOT EXISTS public.cupcake_bottoms (
cupcake_bottom_id serial PRIMARY KEY,
price INTEGER NOT NULL,
bottom_name VARCHAR(50) NOT NULL
);

-- Cupcake Tops
CREATE TABLE IF NOT EXISTS public.cupcake_tops (
cupcake_top_id serial PRIMARY KEY,
price INTEGER NOT NULL,
top_name VARCHAR(50) NOT NULL
);

-- Cupcakes
CREATE TABLE IF NOT EXISTS public.cupcakes (
cupcake_id serial PRIMARY KEY,
cupcake_top_id INTEGER NOT NULL,
cupcake_bottom_id INTEGER NOT NULL,
cupcake_price INTEGER NOT NULL,
quantity INTEGER,
CONSTRAINT fk_cupcake_bottom FOREIGN KEY (cupcake_bottom_id)
    REFERENCES public.cupcake_bottoms (cupcake_bottom_id)
    ON DELETE CASCADE,
    CONSTRAINT fk_cupcake_top FOREIGN KEY (cupcake_top_id)
    REFERENCES public.cupcake_tops (cupcake_top_id)
    ON DELETE CASCADE
);

-- Orderlines
CREATE TABLE IF NOT EXISTS public.orderlines (
orderline_id serial PRIMARY KEY,
order_id INTEGER NOT NULL,
initial_price INTEGER NOT NULL,
CONSTRAINT fk_order FOREIGN KEY (order_id)
    REFERENCES public.orders (order_id)
    ON DELETE CASCADE
    );

-- Cupcakes_Orderlines
CREATE TABLE IF NOT EXISTS public.cupcakes_orderlines (
cupcake_orderline_id serial PRIMARY KEY,
cupcake_id INTEGER NOT NULL,
orderline_id INTEGER NOT NULL,
CONSTRAINT fk_cupcake_id FOREIGN KEY (cupcake_id)
    REFERENCES public.cupcakes (cupcake_id)
    ON DELETE CASCADE,
    CONSTRAINT fk_orderline FOREIGN KEY (orderline_id)
    REFERENCES public.orderlines (orderline_id)
    ON DELETE CASCADE
    );


-- Reset sequences
SELECT setval(pg_get_serial_sequence('public.cupcake_bottoms', 'cupcake_bottom_id'), 1, false);
SELECT setval(pg_get_serial_sequence('public.cupcake_tops', 'cupcake_top_id'), 1, false);
SELECT setval(pg_get_serial_sequence('public.cupcakes', 'cupcake_id'), 1, false);
SELECT setval(pg_get_serial_sequence('public.orderlines', 'orderline_id'), 1, false);
SELECT setval(pg_get_serial_sequence('public.orders', 'order_id'), 1, false);
SELECT setval(pg_get_serial_sequence('public.cupcakes_orderlines', 'cupcake_orderline_id'), 1, false);

END;
