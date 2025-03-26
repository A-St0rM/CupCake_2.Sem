--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.4

-- Started on 2025-03-26 16:59:39 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 228 (class 1259 OID 25115)
-- Name: Cupcake_bottoms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Cupcake_bottoms" (
    cupcake_bottom_id integer NOT NULL,
    order_date date NOT NULL,
    price numeric NOT NULL,
    bottom_name character varying(50) NOT NULL
);


ALTER TABLE public."Cupcake_bottoms" OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 25102)
-- Name: Cupcake_tops; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Cupcake_tops" (
    cupcake_top_id integer NOT NULL,
    order_date date NOT NULL,
    price numeric NOT NULL,
    top_name character varying(50) NOT NULL
);


ALTER TABLE public."Cupcake_tops" OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 25101)
-- Name: Cupcake_tops_cupcake_top_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Cupcake_tops_cupcake_top_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."Cupcake_tops_cupcake_top_id_seq" OWNER TO postgres;

--
-- TOC entry 3441 (class 0 OID 0)
-- Dependencies: 226
-- Name: Cupcake_tops_cupcake_top_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Cupcake_tops_cupcake_top_id_seq" OWNED BY public."Cupcake_tops".cupcake_top_id;


--
-- TOC entry 225 (class 1259 OID 25088)
-- Name: Cupcakes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Cupcakes" (
    cupcake_id integer NOT NULL,
    cupcake_top_id integer NOT NULL,
    cupcake_bottom_id integer NOT NULL,
    cupcake_price numeric NOT NULL,
    quantity integer
);


ALTER TABLE public."Cupcakes" OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 25069)
-- Name: CupcakesOrderlines; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."CupcakesOrderlines" (
    cupcake_orderline_id integer NOT NULL,
    cupcake_id integer NOT NULL,
    orderline_id integer NOT NULL,
    cupcake_price numeric NOT NULL
);


ALTER TABLE public."CupcakesOrderlines" OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 25068)
-- Name: CupcakesOrderlines_cupcake_orderline_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."CupcakesOrderlines_cupcake_orderline_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."CupcakesOrderlines_cupcake_orderline_id_seq" OWNER TO postgres;

--
-- TOC entry 3442 (class 0 OID 0)
-- Dependencies: 222
-- Name: CupcakesOrderlines_cupcake_orderline_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."CupcakesOrderlines_cupcake_orderline_id_seq" OWNED BY public."CupcakesOrderlines".cupcake_orderline_id;


--
-- TOC entry 224 (class 1259 OID 25087)
-- Name: Cupcakes_cupcake_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Cupcakes_cupcake_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."Cupcakes_cupcake_id_seq" OWNER TO postgres;

--
-- TOC entry 3443 (class 0 OID 0)
-- Dependencies: 224
-- Name: Cupcakes_cupcake_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Cupcakes_cupcake_id_seq" OWNED BY public."Cupcakes".cupcake_id;


--
-- TOC entry 215 (class 1259 OID 25009)
-- Name: Customers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Customers" (
    customer_id integer NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    balance numeric
);


ALTER TABLE public."Customers" OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 25026)
-- Name: Customers_customer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Customers_customer_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."Customers_customer_id_seq" OWNER TO postgres;

--
-- TOC entry 3444 (class 0 OID 0)
-- Dependencies: 216
-- Name: Customers_customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Customers_customer_id_seq" OWNED BY public."Customers".customer_id;


--
-- TOC entry 221 (class 1259 OID 25055)
-- Name: Orderlines; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Orderlines" (
    orderline_id integer NOT NULL,
    order_id integer NOT NULL,
    cupcake_orderline_id integer NOT NULL,
    initial_price numeric NOT NULL,
    status_id integer
);


ALTER TABLE public."Orderlines" OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 25054)
-- Name: Orderlines_orderline_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Orderlines_orderline_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."Orderlines_orderline_id_seq" OWNER TO postgres;

--
-- TOC entry 3445 (class 0 OID 0)
-- Dependencies: 220
-- Name: Orderlines_orderline_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Orderlines_orderline_id_seq" OWNED BY public."Orderlines".orderline_id;


--
-- TOC entry 219 (class 1259 OID 25040)
-- Name: Orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Orders" (
    order_id integer NOT NULL,
    customer_id integer NOT NULL,
    order_date date NOT NULL,
    total_price numeric NOT NULL,
    status_id integer NOT NULL
);


ALTER TABLE public."Orders" OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 25039)
-- Name: Orders_customer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Orders_customer_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."Orders_customer_id_seq" OWNER TO postgres;

--
-- TOC entry 3446 (class 0 OID 0)
-- Dependencies: 218
-- Name: Orders_customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Orders_customer_id_seq" OWNED BY public."Orders".customer_id;


--
-- TOC entry 217 (class 1259 OID 25038)
-- Name: Orders_order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Orders_order_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."Orders_order_id_seq" OWNER TO postgres;

--
-- TOC entry 3447 (class 0 OID 0)
-- Dependencies: 217
-- Name: Orders_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Orders_order_id_seq" OWNED BY public."Orders".order_id;


--
-- TOC entry 231 (class 1259 OID 25133)
-- Name: Status; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Status" (
    status_id integer NOT NULL,
    is_paid boolean NOT NULL
);


ALTER TABLE public."Status" OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 25132)
-- Name: Status_status_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Status_status_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."Status_status_id_seq" OWNER TO postgres;

--
-- TOC entry 3448 (class 0 OID 0)
-- Dependencies: 230
-- Name: Status_status_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Status_status_id_seq" OWNED BY public."Status".status_id;


--
-- TOC entry 229 (class 1259 OID 25118)
-- Name: cupcake_bottoms_cupcake_bottom_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cupcake_bottoms_cupcake_bottom_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cupcake_bottoms_cupcake_bottom_id_seq OWNER TO postgres;

--
-- TOC entry 3449 (class 0 OID 0)
-- Dependencies: 229
-- Name: cupcake_bottoms_cupcake_bottom_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cupcake_bottoms_cupcake_bottom_id_seq OWNED BY public."Cupcake_bottoms".cupcake_bottom_id;


--
-- TOC entry 3246 (class 2604 OID 25119)
-- Name: Cupcake_bottoms cupcake_bottom_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cupcake_bottoms" ALTER COLUMN cupcake_bottom_id SET DEFAULT nextval('public.cupcake_bottoms_cupcake_bottom_id_seq'::regclass);


--
-- TOC entry 3245 (class 2604 OID 25105)
-- Name: Cupcake_tops cupcake_top_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cupcake_tops" ALTER COLUMN cupcake_top_id SET DEFAULT nextval('public."Cupcake_tops_cupcake_top_id_seq"'::regclass);


--
-- TOC entry 3244 (class 2604 OID 25091)
-- Name: Cupcakes cupcake_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cupcakes" ALTER COLUMN cupcake_id SET DEFAULT nextval('public."Cupcakes_cupcake_id_seq"'::regclass);


--
-- TOC entry 3243 (class 2604 OID 25072)
-- Name: CupcakesOrderlines cupcake_orderline_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."CupcakesOrderlines" ALTER COLUMN cupcake_orderline_id SET DEFAULT nextval('public."CupcakesOrderlines_cupcake_orderline_id_seq"'::regclass);


--
-- TOC entry 3239 (class 2604 OID 25027)
-- Name: Customers customer_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Customers" ALTER COLUMN customer_id SET DEFAULT nextval('public."Customers_customer_id_seq"'::regclass);


--
-- TOC entry 3242 (class 2604 OID 25058)
-- Name: Orderlines orderline_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Orderlines" ALTER COLUMN orderline_id SET DEFAULT nextval('public."Orderlines_orderline_id_seq"'::regclass);


--
-- TOC entry 3240 (class 2604 OID 25043)
-- Name: Orders order_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Orders" ALTER COLUMN order_id SET DEFAULT nextval('public."Orders_order_id_seq"'::regclass);


--
-- TOC entry 3241 (class 2604 OID 25044)
-- Name: Orders customer_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Orders" ALTER COLUMN customer_id SET DEFAULT nextval('public."Orders_customer_id_seq"'::regclass);


--
-- TOC entry 3247 (class 2604 OID 25136)
-- Name: Status status_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Status" ALTER COLUMN status_id SET DEFAULT nextval('public."Status_status_id_seq"'::regclass);


--
-- TOC entry 3432 (class 0 OID 25115)
-- Dependencies: 228
-- Data for Name: Cupcake_bottoms; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Cupcake_bottoms" (cupcake_bottom_id, order_date, price, bottom_name) FROM stdin;
\.


--
-- TOC entry 3431 (class 0 OID 25102)
-- Dependencies: 227
-- Data for Name: Cupcake_tops; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Cupcake_tops" (cupcake_top_id, order_date, price, top_name) FROM stdin;
\.


--
-- TOC entry 3429 (class 0 OID 25088)
-- Dependencies: 225
-- Data for Name: Cupcakes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Cupcakes" (cupcake_id, cupcake_top_id, cupcake_bottom_id, cupcake_price, quantity) FROM stdin;
\.


--
-- TOC entry 3427 (class 0 OID 25069)
-- Dependencies: 223
-- Data for Name: CupcakesOrderlines; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."CupcakesOrderlines" (cupcake_orderline_id, cupcake_id, orderline_id, cupcake_price) FROM stdin;
\.


--
-- TOC entry 3419 (class 0 OID 25009)
-- Dependencies: 215
-- Data for Name: Customers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Customers" (customer_id, email, password, balance) FROM stdin;
1	test123@hotmail.com	123	100
\.


--
-- TOC entry 3425 (class 0 OID 25055)
-- Dependencies: 221
-- Data for Name: Orderlines; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Orderlines" (orderline_id, order_id, cupcake_orderline_id, initial_price, status_id) FROM stdin;
\.


--
-- TOC entry 3423 (class 0 OID 25040)
-- Dependencies: 219
-- Data for Name: Orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Orders" (order_id, customer_id, order_date, total_price, status_id) FROM stdin;
\.


--
-- TOC entry 3435 (class 0 OID 25133)
-- Dependencies: 231
-- Data for Name: Status; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Status" (status_id, is_paid) FROM stdin;
\.


--
-- TOC entry 3450 (class 0 OID 0)
-- Dependencies: 226
-- Name: Cupcake_tops_cupcake_top_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Cupcake_tops_cupcake_top_id_seq"', 1, false);


--
-- TOC entry 3451 (class 0 OID 0)
-- Dependencies: 222
-- Name: CupcakesOrderlines_cupcake_orderline_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."CupcakesOrderlines_cupcake_orderline_id_seq"', 1, false);


--
-- TOC entry 3452 (class 0 OID 0)
-- Dependencies: 224
-- Name: Cupcakes_cupcake_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Cupcakes_cupcake_id_seq"', 1, false);


--
-- TOC entry 3453 (class 0 OID 0)
-- Dependencies: 216
-- Name: Customers_customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Customers_customer_id_seq"', 1, true);


--
-- TOC entry 3454 (class 0 OID 0)
-- Dependencies: 220
-- Name: Orderlines_orderline_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Orderlines_orderline_id_seq"', 1, false);


--
-- TOC entry 3455 (class 0 OID 0)
-- Dependencies: 218
-- Name: Orders_customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Orders_customer_id_seq"', 1, false);


--
-- TOC entry 3456 (class 0 OID 0)
-- Dependencies: 217
-- Name: Orders_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Orders_order_id_seq"', 1, false);


--
-- TOC entry 3457 (class 0 OID 0)
-- Dependencies: 230
-- Name: Status_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Status_status_id_seq"', 1, false);


--
-- TOC entry 3458 (class 0 OID 0)
-- Dependencies: 229
-- Name: cupcake_bottoms_cupcake_bottom_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cupcake_bottoms_cupcake_bottom_id_seq', 1, false);


--
-- TOC entry 3262 (class 2606 OID 25109)
-- Name: Cupcake_tops Cupcake_tops_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cupcake_tops"
    ADD CONSTRAINT "Cupcake_tops_pkey" PRIMARY KEY (cupcake_top_id);


--
-- TOC entry 3258 (class 2606 OID 25076)
-- Name: CupcakesOrderlines CupcakesOrderlines_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."CupcakesOrderlines"
    ADD CONSTRAINT "CupcakesOrderlines_pkey" PRIMARY KEY (cupcake_orderline_id);


--
-- TOC entry 3260 (class 2606 OID 25095)
-- Name: Cupcakes Cupcakes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cupcakes"
    ADD CONSTRAINT "Cupcakes_pkey" PRIMARY KEY (cupcake_id);


--
-- TOC entry 3250 (class 2606 OID 25032)
-- Name: Customers Customers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Customers"
    ADD CONSTRAINT "Customers_pkey" PRIMARY KEY (customer_id);


--
-- TOC entry 3256 (class 2606 OID 25062)
-- Name: Orderlines Orderlines_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Orderlines"
    ADD CONSTRAINT "Orderlines_pkey" PRIMARY KEY (orderline_id);


--
-- TOC entry 3254 (class 2606 OID 25048)
-- Name: Orders Orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Orders"
    ADD CONSTRAINT "Orders_pkey" PRIMARY KEY (order_id);


--
-- TOC entry 3266 (class 2606 OID 25138)
-- Name: Status Status_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Status"
    ADD CONSTRAINT "Status_pkey" PRIMARY KEY (status_id);


--
-- TOC entry 3248 (class 2606 OID 25037)
-- Name: Customers balance above 0; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public."Customers"
    ADD CONSTRAINT "balance above 0" CHECK ((balance >= (0)::numeric)) NOT VALID;


--
-- TOC entry 3264 (class 2606 OID 25126)
-- Name: Cupcake_bottoms cupcake_bottoms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cupcake_bottoms"
    ADD CONSTRAINT cupcake_bottoms_pkey PRIMARY KEY (cupcake_bottom_id);


--
-- TOC entry 3252 (class 2606 OID 25034)
-- Name: Customers email unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Customers"
    ADD CONSTRAINT "email unique" UNIQUE (email);


--
-- TOC entry 3274 (class 2606 OID 25127)
-- Name: Cupcakes cupcake_bottom_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cupcakes"
    ADD CONSTRAINT cupcake_bottom_id FOREIGN KEY (cupcake_bottom_id) REFERENCES public."Cupcake_bottoms"(cupcake_bottom_id) NOT VALID;


--
-- TOC entry 3272 (class 2606 OID 25096)
-- Name: CupcakesOrderlines cupcake_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."CupcakesOrderlines"
    ADD CONSTRAINT cupcake_id FOREIGN KEY (cupcake_id) REFERENCES public."Cupcakes"(cupcake_id) NOT VALID;


--
-- TOC entry 3269 (class 2606 OID 25082)
-- Name: Orderlines cupcake_orderline_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Orderlines"
    ADD CONSTRAINT cupcake_orderline_id FOREIGN KEY (cupcake_orderline_id) REFERENCES public."CupcakesOrderlines"(cupcake_orderline_id) NOT VALID;


--
-- TOC entry 3275 (class 2606 OID 25110)
-- Name: Cupcakes cupcake_top_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cupcakes"
    ADD CONSTRAINT cupcake_top_id FOREIGN KEY (cupcake_top_id) REFERENCES public."Cupcake_tops"(cupcake_top_id) NOT VALID;


--
-- TOC entry 3267 (class 2606 OID 25049)
-- Name: Orders customer_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Orders"
    ADD CONSTRAINT customer_id FOREIGN KEY (customer_id) REFERENCES public."Customers"(customer_id);


--
-- TOC entry 3270 (class 2606 OID 25063)
-- Name: Orderlines order_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Orderlines"
    ADD CONSTRAINT order_id FOREIGN KEY (order_id) REFERENCES public."Orders"(order_id);


--
-- TOC entry 3273 (class 2606 OID 25077)
-- Name: CupcakesOrderlines orderlines; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."CupcakesOrderlines"
    ADD CONSTRAINT orderlines FOREIGN KEY (orderline_id) REFERENCES public."Orderlines"(orderline_id);


--
-- TOC entry 3271 (class 2606 OID 25139)
-- Name: Orderlines status_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Orderlines"
    ADD CONSTRAINT status_id FOREIGN KEY (status_id) REFERENCES public."Status"(status_id) NOT VALID;


--
-- TOC entry 3268 (class 2606 OID 25144)
-- Name: Orders status_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Orders"
    ADD CONSTRAINT status_id FOREIGN KEY (status_id) REFERENCES public."Status"(status_id) NOT VALID;


-- Completed on 2025-03-26 16:59:39 UTC

--
-- PostgreSQL database dump complete
--

