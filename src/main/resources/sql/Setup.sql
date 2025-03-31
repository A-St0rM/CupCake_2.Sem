BEGIN;

-- Create tables
CREATE TABLE IF NOT EXISTS public.admins
(
    admin_id serial NOT NULL,
    email character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT admins_pkey PRIMARY KEY (admin_id)
    );

CREATE TABLE IF NOT EXISTS public.customers
(
    customer_id serial NOT NULL,
    email character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(50) COLLATE pg_catalog."default" NOT NULL,
    balance numeric, -- only balance remains numeric
    CONSTRAINT customers_pkey PRIMARY KEY (customer_id),
    CONSTRAINT email_unique UNIQUE (email)
    );

CREATE TABLE IF NOT EXISTS public.status
(
    status_id serial NOT NULL,
    is_paid boolean NOT NULL DEFAULT false,
    is_picked_up boolean NOT NULL DEFAULT false,
    CONSTRAINT status_pkey PRIMARY KEY (status_id)
    );

CREATE TABLE IF NOT EXISTS public.orders
(
    order_id serial NOT NULL,
    customer_id integer NOT NULL,
    order_date date NOT NULL,
    total_price integer NOT NULL, -- changed from numeric to integer
    status_id integer NOT NULL,
    CONSTRAINT orders_pkey PRIMARY KEY (order_id),
    CONSTRAINT fk_customer_order FOREIGN KEY (customer_id)
    REFERENCES public.customers (customer_id),
    CONSTRAINT fk_status_order FOREIGN KEY (status_id)
    REFERENCES public.status (status_id)
    );

CREATE TABLE IF NOT EXISTS public.cupcake_bottoms
(
    cupcake_bottom_id serial NOT NULL,
    price integer NOT NULL,
    bottom_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT cupcake_bottoms_pkey PRIMARY KEY (cupcake_bottom_id)
    );

CREATE TABLE IF NOT EXISTS public.cupcake_tops
(
    cupcake_top_id serial NOT NULL,
    price integer NOT NULL,
    top_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT cupcake_tops_pkey PRIMARY KEY (cupcake_top_id)
    );

CREATE TABLE IF NOT EXISTS public.cupcakes
(
    cupcake_id serial NOT NULL,
    cupcake_top_id integer NOT NULL,
    cupcake_bottom_id integer NOT NULL,
    cupcake_price integer NOT NULL,
    quantity integer,
    CONSTRAINT cupcakes_pkey PRIMARY KEY (cupcake_id),
    CONSTRAINT fk_cupcake_bottom FOREIGN KEY (cupcake_bottom_id)
    REFERENCES public.cupcake_bottoms (cupcake_bottom_id),
    CONSTRAINT fk_cupcake_top FOREIGN KEY (cupcake_top_id)
    REFERENCES public.cupcake_tops (cupcake_top_id)
    );

CREATE TABLE IF NOT EXISTS public.orderlines
(
    orderline_id serial NOT NULL,
    order_id integer NOT NULL,
    initial_price integer NOT NULL, -- changed from numeric to integer
    CONSTRAINT orderlines_pkey PRIMARY KEY (orderline_id),
    CONSTRAINT fk_order FOREIGN KEY (order_id)
    REFERENCES public.orders (order_id)
    );

CREATE TABLE IF NOT EXISTS public.cupcakes_orderlines
(
    cupcake_orderline_id serial NOT NULL,
    cupcake_id integer NOT NULL,
    orderline_id integer NOT NULL,
    cupcake_price integer NOT NULL,
    CONSTRAINT cupcakes_orderlines_pkey PRIMARY KEY (cupcake_orderline_id),
    CONSTRAINT fk_cupcake_id FOREIGN KEY (cupcake_id)
    REFERENCES public.cupcakes (cupcake_id),
    CONSTRAINT fk_orderline FOREIGN KEY (orderline_id)
    REFERENCES public.orderlines (orderline_id)
    );

-- Reset sequences
SELECT setval(pg_get_serial_sequence('public.cupcake_bottoms', 'cupcake_bottom_id'), 1, false);
SELECT setval(pg_get_serial_sequence('public.cupcake_tops', 'cupcake_top_id'), 1, false);
SELECT setval(pg_get_serial_sequence('public.cupcakes', 'cupcake_id'), 1, false);
SELECT setval(pg_get_serial_sequence('public.orderlines', 'orderline_id'), 1, false);
SELECT setval(pg_get_serial_sequence('public.orders', 'order_id'), 1, false);
SELECT setval(pg_get_serial_sequence('public.cupcakes_orderlines', 'cupcake_orderline_id'), 1, false);

END;
