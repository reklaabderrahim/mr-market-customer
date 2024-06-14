SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SET check_function_bodies = FALSE;
SET client_min_messages = WARNING;
SET row_security = OFF;

CREATE SCHEMA IF NOT EXISTS customer;
ALTER SCHEMA customer OWNER TO market_owner;
SET search_path = customer, pg_catalog;
SET default_tablespace = '';
SET default_with_oids = FALSE;

CREATE TABLE if not exists customer
(
    id              BIGINT                  NOT NULL,
    uuid            UUID                    NOT NULL,
    create_date     TIMESTAMP               NOT NULL,
    first_name      CHARACTER VARYING(50)   NOT NULL,
    last_name       CHARACTER VARYING(50)   NOT NULL,
    birth_date      DATE                    NULL,
    gender          CHARACTER VARYING(6)    NULL,
    mail            CHARACTER VARYING(255)  NOT NULL,
    login_date      TIMESTAMP NULL,
    active          BOOLEAN DEFAULT TRUE    NOT NULL,
    PRIMARY KEY (id),
    constraint customer_uuid_unique UNIQUE (uuid)
);

comment on table customer is 'Customer main table';
comment on column customer.id is 'The customer ID';
comment on column customer.uuid is 'The customer unique identifier';
comment on column customer.create_date is 'The customer creation (insert) date';
comment on column customer.first_name is 'The customer first name';
comment on column customer.last_name is 'The customer last name';
comment on column customer.birth_date is 'The customer birth date';
comment on column customer.gender is 'The customer gender';
comment on column customer.mail is 'The customer email';
comment on column customer.login_date is 'The customer last login date';
comment on column customer.active is 'The customer status';

ALTER TABLE if exists customer OWNER TO market_owner;

CREATE SEQUENCE if not exists customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE if exists customer_id_seq OWNER TO market_owner;


CREATE TABLE if not exists collection_item
(
    id              BIGINT                  NOT NULL,
    uuid            UUID                    NOT NULL,
    customer_id     BIGINT                  NOT NULL,
    product_uuid    UUID                    NOT NULL,
    create_date     TIMESTAMP               NOT NULL,
    update_date     TIMESTAMP               NULL,
    delete_date     TIMESTAMP               NULL,
    delete_status   SMALLINT                NULL,
    is_gift         BOOLEAN DEFAULT FALSE   NOT NULL,
    PRIMARY KEY (id),
    constraint collection_item_uuid_unique UNIQUE (uuid),
    CONSTRAINT collection_item__customer_id_fk FOREIGN KEY (customer_id) REFERENCES customer (id)
);

ALTER TABLE if exists collection_item OWNER TO market_owner;

CREATE SEQUENCE if not exists collection_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE if exists collection_item_id_seq OWNER TO market_owner;

comment on table collection_item is 'Collection item table';
comment on column collection_item.id is 'The collection_item ID';
comment on column collection_item.uuid is 'The collection_item unique identifier';
comment on column collection_item.customer_id is 'Customer Id (Foreign key)';
comment on column collection_item.product_uuid is 'The product unique identifier';
comment on column collection_item.create_date is 'The collection_item creation date';
comment on column collection_item.update_date is 'The collection_item updating date';
comment on column collection_item.delete_date is 'The collection_item deleting date';
comment on column collection_item.delete_status is 'The collection_item deleting status: 0=Not Deleted, 1=Deleted Item Being Returned, 2=Deleted automatically from Portfolio (wrongly added), 3=Deleted Duplicated Item, 4=Deleted By Customer from Portfolio, 9=Historical Deletion';
comment on column collection_item.is_gift is 'Product is receive as a gift';


/***************************************************************************************/
SELECT pg_catalog.setval('customer_id_seq', 1, FALSE);
SELECT pg_catalog.setval('collection_item_id_seq', 1, FALSE);


REVOKE ALL ON SCHEMA customer FROM PUBLIC;
REVOKE ALL ON SCHEMA customer FROM market_owner;
GRANT ALL ON SCHEMA customer TO market_owner;
GRANT USAGE ON SCHEMA customer TO market_select;
GRANT USAGE ON SCHEMA customer TO market_update;


REVOKE ALL ON TABLE customer FROM PUBLIC;
REVOKE ALL ON TABLE customer FROM market_owner;
GRANT ALL ON TABLE customer TO market_owner;
GRANT SELECT, INSERT, DELETE, TRUNCATE, UPDATE ON TABLE customer TO market_update;
GRANT SELECT ON TABLE customer TO market_select;

REVOKE ALL ON SEQUENCE customer_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE customer_id_seq FROM market_owner;
GRANT ALL ON SEQUENCE customer_id_seq TO market_owner;
GRANT USAGE ON SEQUENCE customer_id_seq TO market_select;


REVOKE ALL ON TABLE collection_item FROM PUBLIC;
REVOKE ALL ON TABLE collection_item FROM market_owner;
GRANT ALL ON TABLE collection_item TO market_owner;
GRANT SELECT, INSERT, DELETE, TRUNCATE, UPDATE ON TABLE collection_item TO market_update;
GRANT SELECT ON TABLE collection_item TO market_select;

REVOKE ALL ON SEQUENCE collection_item_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE collection_item_id_seq FROM market_owner;
GRANT ALL ON SEQUENCE collection_item_id_seq TO market_owner;
GRANT USAGE ON SEQUENCE collection_item_id_seq TO market_select;



ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA customer REVOKE ALL ON SEQUENCES FROM PUBLIC;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA customer REVOKE ALL ON SEQUENCES FROM market_owner;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA customer GRANT ALL ON SEQUENCES TO market_owner;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA customer GRANT USAGE ON SEQUENCES TO market_select;


ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA customer REVOKE ALL ON FUNCTIONS FROM PUBLIC;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA customer REVOKE ALL ON FUNCTIONS FROM market_owner;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA customer GRANT ALL ON FUNCTIONS TO market_owner;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA customer GRANT ALL ON FUNCTIONS TO market_select;


ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA customer REVOKE ALL ON TABLES FROM PUBLIC;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA customer REVOKE ALL ON TABLES FROM market_owner;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA customer GRANT ALL ON TABLES TO market_owner;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA customer GRANT SELECT, INSERT, DELETE, TRUNCATE, UPDATE ON TABLES TO market_update;
ALTER DEFAULT PRIVILEGES FOR ROLE market_owner IN SCHEMA customer GRANT SELECT ON TABLES TO market_select;
