--liquibase formatted sql

--changeset holichenko:GEN-1

create table movies
(
    id         bigserial primary key,
    title      text      not null,
    start_time timestamp not null,
    duration   int       not null,
    hall       int       not null,
    created_at timestamp not null default now()
);

create table orders
(
    id           bigserial primary key,
    total_amount decimal(10, 2) not null,
    total_seats  int            not null,
    created_at   timestamp      not null default now(),
    movie_id     bigint,
    CONSTRAINT movies_fk FOREIGN KEY (movie_id) REFERENCES movies (id) ON UPDATE CASCADE ON DELETE CASCADE
);