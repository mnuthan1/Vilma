create table "file"
(
    id varchar(20) not null,
    name varchar(100) not null,
    path varchar(200) not null,
    size number not null,
    checksum varchar(200) not null,
    primary key(id)
);