create table follow
(
	id int auto_increment
		primary key,
	accountId int not null,
	followId int not null
);

