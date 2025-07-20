--liquibase formatted sql

--changeset Mavili-Mordecai:001-create-users-table.sql
CREATE SEQUENCE users_seq INCREMENT BY 50;

CREATE TABLE users(
    id BIGINT PRIMARY KEY DEFAULT nextval('users_seq'),
    uuid UUID NOT NULL UNIQUE,
    login VARCHAR(255) NOT NULL UNIQUE,
    password TEXT NOT NULL
);

ALTER SEQUENCE users_seq OWNED BY users.id;
--rollback DROP TABLE users;