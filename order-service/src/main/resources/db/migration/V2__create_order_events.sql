create table order_events
(
    id       bigint NOT NULL AUTO_INCREMENT,
    order_number     varchar(50) not null,
    event_id     varchar(50) not null unique,
    event_type    varchar(20) not null,
    payload       text not null,
	created_at    timestamp not null,
    updated_at    timestamp,
    primary key (id)
);