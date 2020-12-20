create table activities (
        id bigserial primary key,
        is_hidden boolean null default false,
        scheduled_at timestamp,
        duration integer,
        descr varchar(255),
        created_at timestamp default now(),
        contact varchar(255)
);

create table images (
        id bigserial primary key,
        url varchar(255),
        is_hidden boolean null default false
);

create table medals (
        id bigserial primary key,
        s_hidden boolean null default false,
        name varchar(255)
);

create table organizers (
        id         bigserial primary key,
        name       varchar(255),
        email      varchar(255),
        is_hidden boolean null default false
);

create table roles (
        id         bigserial primary key,
        name       varchar(255)
);

create table services (
        id         bigserial primary key,
        name       varchar(255),
        is_hidden boolean null default false,
        price real,
        duration integer
);

create table students (
        id         bigserial primary key,
        name       varchar(255),
        is_hidden boolean null default false,
        login varchar(255),
        birthday timestamp
);

create table tutors (
        id         bigserial primary key,
        name       varchar(255),
        is_hidden boolean null default false,
        exp varchar(255),
        age integer,
        place varchar(255),
        location varchar(255),
        isConfirmed boolean,
        contact varchar(255),
        email varchar(255)
);

alter table activities add column organizer_id bigint references organizers;
alter table services add column tutor_id bigint references tutors;
alter table students add column tutor_id bigint references tutors;
alter table students add column organizer_id bigint references organizers;
alter table tutors add column student_id bigint references students;
alter table tutors add column education varchar(255);
alter table tutors add column job varchar(255);
alter table tutors add column format varchar(255);
alter table tutors add column short_education varchar(255);

create table courses (
            id         bigserial primary key,
            name       varchar(255),
            is_hidden boolean null default false,
            created_at timestamp default now(),
            format varchar(255),
            scheduled_to timestamp,
            tutor_id bigint references tutors
);

alter table students add column email varchar(255);
create unique index unique_not_hidden_shared_client
    on students (email) where (is_hidden is null or is_hidden is false);
alter table students add column phone varchar(255);
