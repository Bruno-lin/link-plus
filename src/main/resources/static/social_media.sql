create table social_media
(
	id int auto_increment
		primary key,
	name varchar(64) not null,
	logoUrl varchar(512) not null,
	constraint social_media_name_uindex
		unique (name)
);

