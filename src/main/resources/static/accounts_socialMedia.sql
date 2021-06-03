create table accounts_socialMedia
(
	id int auto_increment
		primary key,
	accountId int not null,
	socialMediaId int not null,
	content varchar(512) not null,
	enable tinyint(1) default 1 null
);

