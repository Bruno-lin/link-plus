create table accounts
(
	id int auto_increment
		primary key,
	username varchar(32) not null,
	secretKey varchar(255) not null,
	nickname varchar(32) not null,
	displayNumber int default 0 null,
	background varchar(512) null,
	avatar varchar(512) null,
	constraint accounts_nickname_uindex
		unique (nickname),
	constraint accounts_username_uindex
		unique (username)
);

