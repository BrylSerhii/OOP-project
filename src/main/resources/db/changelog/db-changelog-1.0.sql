-- --liquibase formatted sql

--changeset artem:1
CREATE TABLE users
(
    id         bigserial PRIMARY KEY,
    first_name text        not null,
    last_name  text        not null,
    email      text UNIQUE not null,
    password   text        not null,
    role       text        not null,
    score int   default 0,
    performance int   default 0
);


--changeset artem:2
create table lesson
(
    id    bigserial PRIMARY KEY,
    title text not null
);

--changeset artem:3
create table theory(
    id bigserial primary key ,
    title text not null ,
    lesson_id bigint not null references lesson(id),
    html text  ,
    author text
);

--changeset artem:4
create table test(
    id bigserial primary key ,
    lesson_id bigint not null references lesson(id),
    test text not null ,
    questions_amount int,
    points int ,
    author text not null
);

--changeset artem:5
create table quiz(
    id bigserial primary key,
    test_id bigint not null references test(id),
    question text not null ,
    option_a text not null ,
    option_b text not null ,
    option_c text not null ,
    true_option text not null ,
    points int not null
);

--changeset artem:6
create table users_test(
    id bigserial primary key ,
    users_id bigint not null references users(id),
    test_id bigint not null  references test(id),
    points int not null ,
    received_points int,
    performance float
);
