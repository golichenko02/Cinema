--liquibase formatted sql

--changeset holichenko:GEN-1

create sequence movies_id_seq start with 1 increment 1;

create table movies
(
    id         bigint primary key default nextval('movies_id_seq'),
    title      text      not null,
    start_time timestamp not null,
    duration   int       not null,
    hall       int       not null,
    created_at timestamp not null default now()
);

create sequence orders_id_seq start with 1 increment 1;

create table orders
(
    id           bigint primary key      default nextval('orders_id_seq'),
    total_amount decimal(10, 2) not null,
    total_seats  int            not null,
    created_at   timestamp      not null default now(),
    movie_id     bigint,
    CONSTRAINT movies_fk FOREIGN KEY (movie_id) REFERENCES movies (id) ON UPDATE CASCADE ON DELETE CASCADE
);
