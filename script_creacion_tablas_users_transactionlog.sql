-- master.dbo.transaction_log definition

-- Drop table

-- DROP TABLE master.dbo.transaction_log;

CREATE TABLE master.dbo.transaction_log (
	id int IDENTITY(0,1) NOT NULL,
	username varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	api varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	api_method varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	response_code varchar(5) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	response_description varchar(1000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	created_date datetime DEFAULT getdate() NULL,
	uuid varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
);


-- master.dbo.users definition

-- Drop table

-- DROP TABLE master.dbo.users;

CREATE TABLE master.dbo.users (
	id int IDENTITY(0,1) NOT NULL,
	username varchar(100) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	password varchar(200) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL,
	status bit NULL,
	created_date datetime DEFAULT getdate() NULL,
	CONSTRAINT user_PK PRIMARY KEY (id),
	CONSTRAINT user_UN UNIQUE (username)
);
CREATE UNIQUE NONCLUSTERED INDEX user_UN ON master.dbo.users (username);