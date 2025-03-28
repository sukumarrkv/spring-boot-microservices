create table if not exists products
(
    id bigint auto_increment primary key,
    code        varchar(20) not null unique,
    name        varchar(100) not null,
    description varchar(900),
    image_url   varchar(300),
    price       decimal(8,2) not null
);