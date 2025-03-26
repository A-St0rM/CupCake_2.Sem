
BEGIN;

CREATE TABLE IF NOT EXISTS public."Cupcake_bottoms"
(
    cupcake_bottom_id integer NOT NULL DEFAULT nextval('cupcake_bottoms_cupcake_bottom_id_seq'::regclass),
    order_date date NOT NULL,
    price numeric NOT NULL,
    bottom_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT cupcake_bottoms_pkey PRIMARY KEY (cupcake_bottom_id)
    );

CREATE TABLE IF NOT EXISTS public."Cupcake_tops"
(
    cupcake_top_id serial NOT NULL,
    order_date date NOT NULL,
    price numeric NOT NULL,
    top_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Cupcake_tops_pkey" PRIMARY KEY (cupcake_top_id)
    );

CREATE TABLE IF NOT EXISTS public."Cupcakes"
(
    cupcake_id serial NOT NULL,
    cupcake_top_id integer NOT NULL,
    cupcake_bottom_id integer NOT NULL,
    cupcake_price numeric NOT NULL,
    quantity integer,
    CONSTRAINT "Cupcakes_pkey" PRIMARY KEY (cupcake_id)
    );

CREATE TABLE IF NOT EXISTS public."CupcakesOrderlines"
(
    cupcake_orderline_id serial NOT NULL,
    cupcake_id integer NOT NULL,
    orderline_id integer NOT NULL,
    cupcake_price numeric NOT NULL,
    CONSTRAINT "CupcakesOrderlines_pkey" PRIMARY KEY (cupcake_orderline_id)
    );

CREATE TABLE IF NOT EXISTS public."Customers"
(
    customer_id serial NOT NULL,
    email character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(50) COLLATE pg_catalog."default" NOT NULL,
    balance numeric,
    CONSTRAINT "Customers_pkey" PRIMARY KEY (customer_id),
    CONSTRAINT "email unique" UNIQUE (email)
    );

CREATE TABLE IF NOT EXISTS public."Orderlines"
(
    orderline_id serial NOT NULL,
    order_id integer NOT NULL,
    cupcake_orderline_id integer NOT NULL,
    initial_price numeric NOT NULL,
    status_id integer,
    CONSTRAINT "Orderlines_pkey" PRIMARY KEY (orderline_id)
    );

CREATE TABLE IF NOT EXISTS public."Orders"
(
    order_id serial NOT NULL,
    customer_id serial NOT NULL,
    order_date date NOT NULL,
    total_price numeric NOT NULL,
    status_id integer NOT NULL,
    CONSTRAINT "Orders_pkey" PRIMARY KEY (order_id)
    );

CREATE TABLE IF NOT EXISTS public."Status"
(
    status_id serial NOT NULL,
    is_paid boolean NOT NULL,
    CONSTRAINT "Status_pkey" PRIMARY KEY (status_id)
    );

ALTER TABLE IF EXISTS public."Cupcakes"
    ADD CONSTRAINT cupcake_bottom_id FOREIGN KEY (cupcake_bottom_id)
    REFERENCES public."Cupcake_bottoms" (cupcake_bottom_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public."Cupcakes"
    ADD CONSTRAINT cupcake_top_id FOREIGN KEY (cupcake_top_id)
    REFERENCES public."Cupcake_tops" (cupcake_top_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public."CupcakesOrderlines"
    ADD CONSTRAINT cupcake_id FOREIGN KEY (cupcake_id)
    REFERENCES public."Cupcakes" (cupcake_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public."CupcakesOrderlines"
    ADD CONSTRAINT orderlines FOREIGN KEY (orderline_id)
    REFERENCES public."Orderlines" (orderline_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public."Orderlines"
    ADD CONSTRAINT cupcake_orderline_id FOREIGN KEY (cupcake_orderline_id)
    REFERENCES public."CupcakesOrderlines" (cupcake_orderline_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public."Orderlines"
    ADD CONSTRAINT order_id FOREIGN KEY (order_id)
    REFERENCES public."Orders" (order_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public."Orderlines"
    ADD CONSTRAINT status_id FOREIGN KEY (status_id)
    REFERENCES public."Status" (status_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public."Orders"
    ADD CONSTRAINT customer_id FOREIGN KEY (customer_id)
    REFERENCES public."Customers" (customer_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public."Orders"
    ADD CONSTRAINT status_id FOREIGN KEY (status_id)
    REFERENCES public."Status" (status_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;

END;