

create table account
(
    id            bigint auto_increment,
    username      varchar(32)   not null,
    secretKey     varchar(255)  not null,
    nickname      varchar(32)   not null,
    slogan        varchar(144)  null,
    avatar        varchar(512)  null,
    background    varchar(512)  null,
    displayNumber int default 0 null,
    constraint accounts_id_uindex
        unique (id),
    constraint accounts_nickname_uindex
        unique (nickname),
    constraint accounts_username_uindex
        unique (username)
);

alter table account
    add primary key (id);

create table account_socialMedia
(
    id            bigint auto_increment,
    accountId     int                  not null,
    socialMediaId int                  not null,
    content       varchar(512)         not null,
    enable        tinyint(1) default 1 null,
    constraint accounts_socialMedia_id_uindex
        unique (id)
);

alter table account_socialMedia
    add primary key (id);

create table follow
(
    id        bigint auto_increment,
    accountId int not null,
    followId  int not null,
    constraint follow_id_uindex
        unique (id)
);

alter table follow
    add primary key (id);

create table social_media
(
    id      bigint auto_increment,
    name    varchar(64)  not null,
    logoUrl varchar(512) not null,
    constraint social_media_id_uindex
        unique (id),
    constraint social_media_name_uindex
        unique (name)
);

alter table social_media
    add primary key (id);

