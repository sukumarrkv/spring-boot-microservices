create table orders
(
    id                        bigint NOT NULL AUTO_INCREMENT,
    order_number              varchar(50) not null unique,
    username                  varchar(20) not null,
    customer_name             varchar(20) not null,
    customer_email            varchar(50) not null,
    customer_phone            varchar(20) not null,
    delivery_address_line1    varchar(50) not null,
    delivery_address_line2    varchar(50),
    delivery_address_city     varchar(20) not null,
    delivery_address_state    varchar(20) not null,
    delivery_address_zip_code varchar(10) not null,
    delivery_address_country  varchar(20) not null,
    status                    varchar(10) not null,
    comments                  varchar(100),
    created_at                timestamp,
    updated_at                timestamp,
    primary key (id)
);

create table order_items
(
    id       bigint NOT NULL AUTO_INCREMENT,
    code     varchar(20) not null,
    name     varchar(100) not null,
    price    decimal(8,2) not null,
    quantity int not null,
    primary key (id),
    order_id bigint not null references orders (id)
);