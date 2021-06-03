create database linkplus if not exists;

create table accounts
(
    id            int auto_increment
        primary key,
    username      varchar(32)   not null,
    secretKey     varchar(255)  not null,
    nickname      varchar(32)   not null,
    displayNumber int default 0 null,
    background    varchar(512)  null,
    avatar        varchar(512)  null,
    constraint accounts_nickname_uindex
        unique (nickname),
    constraint accounts_username_uindex
        unique (username)
);

create table accounts_socialMedia
(
    id            int auto_increment
        primary key,
    accountId     int                  not null,
    socialMediaId int                  not null,
    content       varchar(512)         not null,
    enable        tinyint(1) default 1 null
);

create table follow
(
    id        int auto_increment
        primary key,
    accountId int not null,
    followId  int not null
);

create table social_media
(
    id      int auto_increment
        primary key,
    name    varchar(64)  not null,
    logoUrl varchar(512) not null,
    constraint social_media_name_uindex
        unique (name)
);

