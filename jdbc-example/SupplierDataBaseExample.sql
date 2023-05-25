--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2023-05-25 17:22:24

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

DROP DATABASE "SupplierDataBase";
--
-- TOC entry 3354 (class 1262 OID 16403)
-- Name: SupplierDataBase; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "SupplierDataBase" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United Kingdom.1252';


ALTER DATABASE "SupplierDataBase" OWNER TO postgres;

\connect "SupplierDataBase"

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

--
-- TOC entry 3355 (class 0 OID 0)
-- Dependencies: 3354
-- Name: DATABASE "SupplierDataBase"; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE "SupplierDataBase" IS 'This data Base is a demo. For Practice';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 16461)
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    id integer NOT NULL,
    firstname character varying(50),
    lastname character varying(50),
    city character varying(50),
    country character varying(50),
    phone character varying(20)
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16466)
-- Name: orderinfo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orderinfo (
    id integer NOT NULL,
    orderdate date,
    ordernumber character varying(20),
    customerid integer,
    totalamount numeric(10,2)
);


ALTER TABLE public.orderinfo OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16476)
-- Name: orderitem; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orderitem (
    id integer NOT NULL,
    orderid integer,
    productid integer,
    unitprice numeric(10,2),
    quantity integer
);


ALTER TABLE public.orderitem OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16451)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    id integer NOT NULL,
    productname character varying(100),
    supplierid integer,
    unitprice numeric(10,2),
    package character varying(50),
    isdiscontinued boolean
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16446)
-- Name: supplier; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.supplier (
    id integer NOT NULL,
    companyname character varying(100),
    contactname character varying(50),
    contacttitle character varying(50),
    city character varying(50),
    country character varying(50),
    phone character varying(20),
    fax character varying(20)
);


ALTER TABLE public.supplier OWNER TO postgres;

--
-- TOC entry 3346 (class 0 OID 16461)
-- Dependencies: 216
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.customer VALUES (10, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '982847874');
INSERT INTO public.customer VALUES (11, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '982847874');
INSERT INTO public.customer VALUES (12, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '982847874');
INSERT INTO public.customer VALUES (13, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '982847874');
INSERT INTO public.customer VALUES (14, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '982847874');
INSERT INTO public.customer VALUES (15, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '982847874');
INSERT INTO public.customer VALUES (17, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '7655645');
INSERT INTO public.customer VALUES (1, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '+1 123-456-7890');
INSERT INTO public.customer VALUES (2, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '+44 987-654-3210');
INSERT INTO public.customer VALUES (3, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '+61 123-456-7890');
INSERT INTO public.customer VALUES (4, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '+595 21 987-654');
INSERT INTO public.customer VALUES (5, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '+595 61 987-654');
INSERT INTO public.customer VALUES (18, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '205616');
INSERT INTO public.customer VALUES (19, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '205616');
INSERT INTO public.customer VALUES (20, 'JHONY', 'BRAVO', 'TANGAMANDAPIO', 'ISLANDIA', '205616');


--
-- TOC entry 3347 (class 0 OID 16466)
-- Dependencies: 217
-- Data for Name: orderinfo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.orderinfo VALUES (1, '2023-05-01', 'ORD-001', 1, 120.50);
INSERT INTO public.orderinfo VALUES (2, '2023-05-02', 'ORD-002', 1, 75.80);
INSERT INTO public.orderinfo VALUES (3, '2023-05-03', 'ORD-003', 2, 210.25);
INSERT INTO public.orderinfo VALUES (4, '2023-05-04', 'ORD-004', 3, 50.00);
INSERT INTO public.orderinfo VALUES (5, '2023-05-05', 'ORD-005', 4, 80.00);
INSERT INTO public.orderinfo VALUES (6, '2023-05-06', 'ORD-006', 5, 150.00);


--
-- TOC entry 3348 (class 0 OID 16476)
-- Dependencies: 218
-- Data for Name: orderitem; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.orderitem VALUES (3, 2, 3, 15.75, 2);
INSERT INTO public.orderitem VALUES (4, 3, 1, 10.99, 1);
INSERT INTO public.orderitem VALUES (5, 3, 4, 18.25, 4);
INSERT INTO public.orderitem VALUES (6, 4, 5, 9.99, 2);
INSERT INTO public.orderitem VALUES (7, 5, 6, 12.50, 3);


--
-- TOC entry 3345 (class 0 OID 16451)
-- Dependencies: 215
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product VALUES (1, 'Product A', 1, 10.99, 'Box', false);
INSERT INTO public.product VALUES (2, 'Product B', 2, 8.50, 'Bag', false);
INSERT INTO public.product VALUES (3, 'Product C', 1, 15.75, 'Box', true);
INSERT INTO public.product VALUES (4, 'Product D', 3, 18.25, 'Case', false);
INSERT INTO public.product VALUES (5, 'Producto E', 4, 9.99, 'Caja', false);
INSERT INTO public.product VALUES (6, 'Producto F', 5, 12.50, 'Bolsa', false);


--
-- TOC entry 3344 (class 0 OID 16446)
-- Dependencies: 214
-- Data for Name: supplier; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.supplier VALUES (2, 'Supplier Y', 'Jane Smith', 'Account Manager', 'London', 'UK', '+44 20 1234', '+44 20 5678');
INSERT INTO public.supplier VALUES (3, 'Supplier Z', 'Mike Williams', 'Sales Representative', 'Sydney', 'Australia', '+61 2 1234', '+61 2 5678');
INSERT INTO public.supplier VALUES (4, 'Proveedor W', 'Roberto Wilson', 'Gerente de Ventas', 'Asunción', 'Paraguay', '+595 21 1234', '+595 21 5678');
INSERT INTO public.supplier VALUES (5, 'Proveedor V', 'María Martínez', 'gerente', 'yaguaron', 'Paraguay', '+595 61 1234', '+595 61 5678');
INSERT INTO public.supplier VALUES (1, 'PROVEEDOR', 'JOHNSON', 'MANAGER', 'LONDON', 'ENGLAND', '+1 555-1234', '+1 555-5678');


--
-- TOC entry 3193 (class 2606 OID 16465)
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 3195 (class 2606 OID 16470)
-- Name: orderinfo orderinfo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderinfo
    ADD CONSTRAINT orderinfo_pkey PRIMARY KEY (id);


--
-- TOC entry 3197 (class 2606 OID 16480)
-- Name: orderitem orderitem_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderitem
    ADD CONSTRAINT orderitem_pkey PRIMARY KEY (id);


--
-- TOC entry 3191 (class 2606 OID 16455)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 3189 (class 2606 OID 16450)
-- Name: supplier supplier_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.supplier
    ADD CONSTRAINT supplier_pkey PRIMARY KEY (id);


--
-- TOC entry 3199 (class 2606 OID 16471)
-- Name: orderinfo orderinfo_customerid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderinfo
    ADD CONSTRAINT orderinfo_customerid_fkey FOREIGN KEY (customerid) REFERENCES public.customer(id);


--
-- TOC entry 3200 (class 2606 OID 16481)
-- Name: orderitem orderitem_orderid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderitem
    ADD CONSTRAINT orderitem_orderid_fkey FOREIGN KEY (orderid) REFERENCES public.orderinfo(id);


--
-- TOC entry 3201 (class 2606 OID 16486)
-- Name: orderitem orderitem_productid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderitem
    ADD CONSTRAINT orderitem_productid_fkey FOREIGN KEY (productid) REFERENCES public.product(id);


--
-- TOC entry 3198 (class 2606 OID 16456)
-- Name: product product_supplierid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_supplierid_fkey FOREIGN KEY (supplierid) REFERENCES public.supplier(id);


-- Completed on 2023-05-25 17:22:24

--
-- PostgreSQL database dump complete
--

