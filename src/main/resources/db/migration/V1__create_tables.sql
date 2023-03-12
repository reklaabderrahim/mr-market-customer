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

ALTER TABLE if exists customer OWNER TO customerOwner;

CREATE SEQUENCE if not exists customer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE if exists customer_id_seq OWNER TO customerOwner;


CREATE TABLE if not exists collection_item
(
    id              BIGINT                  NOT NULL,
    uuid            UUID                    NOT NULL,
    customer_id     BIGINT                  NOT NULL,
    product_uuid    UUID                    NOT NULL,
    create_date     TIMESTAMP               NOT NULL,
    update_date     TIMESTAMP               NULL,
    delete_date     TIMESTAMP               NULL,
    delete_status   INT                     NULL,
    is_gift         BOOLEAN DEFAULT FALSE   NOT NULL,
    PRIMARY KEY (id),
    constraint collection_item_uuid_unique UNIQUE (uuid),
    CONSTRAINT collection_item__customer_id_fk FOREIGN KEY (customer_id) REFERENCES customer (id)
);

ALTER TABLE if exists collection_item OWNER TO customerOwner;

CREATE SEQUENCE if not exists collection_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE if exists collection_item_id_seq OWNER TO customerOwner;

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