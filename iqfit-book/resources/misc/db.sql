CREATE TABLE customer (
	id				bigserial NOT NULL,
	firstname		character varying(50),
	middlename		character varying(10),
	surname			character varying(50),
	emailaddress	character varying(100) NOT NULL,

	PRIMARY KEY(id),
	UNIQUE(emailaddress)
)
WITH (
  OIDS=FALSE
);


CREATE TABLE orders (
	id				bigserial NOT NULL,
	customer_id		bigint REFERENCES customer(id),
	ordernumber		character varying(128) NOT NULL,
	transaction_id	character varying(128),
	orderdate		timestamp without time zone NOT NULL,
	status			character varying(8) NOT NULL,
	downloaded		boolean NOT NULL default 'false',

	PRIMARY KEY (id),
	UNIQUE (ordernumber)
)
WITH (
  OIDS=FALSE
);


CREATE TABLE emailaddress (
	id				bigserial NOT NULL,
	uuid			character varying(128) NOT NULL,
	emailaddress	character varying(100) NOT NULL,

	PRIMARY KEY(id),
	UNIQUE(emailaddress),
	UNIQUE(uuid)
)
WITH (
  OIDS=FALSE
);


CREATE ROLE role_iqfit
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;


CREATE ROLE iqfit LOGIN
  ENCRYPTED PASSWORD 'md52e994ffa5ff1ea564c7b286bf91ecec4'
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;
GRANT role_iqfit TO iqfit;


ALTER TABLE customer
  OWNER TO postgres;
GRANT SELECT, UPDATE, INSERT, DELETE, REFERENCES ON TABLE customer TO role_iqfit;
REVOKE ALL ON TABLE customer FROM postgres;


ALTER TABLE orders
  OWNER TO postgres;
GRANT ALL ON TABLE orders TO postgres;
GRANT SELECT, UPDATE, INSERT, REFERENCES ON TABLE orders TO role_iqfit;


ALTER TABLE emailaddress
  OWNER TO postgres;
GRANT ALL ON TABLE emailaddress TO postgres;
GRANT SELECT, INSERT, REFERENCES ON TABLE emailaddress TO role_iqfit;


ALTER TABLE customer_id_seq
  OWNER TO postgres;
GRANT ALL ON TABLE customer_id_seq TO role_iqfit;
REVOKE ALL ON TABLE customer_id_seq FROM postgres;

ALTER TABLE orders_id_seq
  OWNER TO postgres;
GRANT ALL ON TABLE orders_id_seq TO postgres;
GRANT ALL ON TABLE orders_id_seq TO role_iqfit;

ALTER TABLE emailaddress_id_seq
  OWNER TO postgres;
GRANT ALL ON TABLE emailaddress_id_seq TO role_iqfit;
REVOKE ALL ON TABLE emailaddress_id_seq FROM postgres;