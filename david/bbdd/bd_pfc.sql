DROP DATABASE IF EXISTS pfc;
CREATE DATABASE pfc;
USE pfc;

DROP TABLE IF EXISTS user;
CREATE TABLE user(
	id_user 	integer NOT NULL AUTO_INCREMENT,
	nick		varchar(20) NOT NULL,
	name 		varchar(20) NOT NULL,
	surname1 	varchar(20) NOT NULL,
	surname2 	varchar(20),
	email_user	varchar(50) NOT NULL,
	city		varchar(20) NOT NULL,
	school  	varchar(50) NOT NULL,
	email_school    varchar(50),
	type_user	varchar(20) NOT NULL,
	pass		varchar(20),
	activated 	bool	    DEFAULT false,
	date_insertion	date NOT NULL,
	activation_key  varchar(20),

	PRIMARY KEY (id_user),
	UNIQUE KEY (nick)	
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS team;
CREATE TABLE team (
  id_team 	integer  NOT NULL AUTO_INCREMENT,
  name 		varchar(20)  NOT NULL,
  id_founded 	integer  NOT NULL,

  PRIMARY KEY (id_team),
  UNIQUE KEY (name)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS user_team;
CREATE TABLE user_team (
  id_user	integer	 NOT NULL,
  id_team 	integer  NOT NULL,
  active 	integer  DEFAULT 0,

  PRIMARY KEY (id_user, id_team),
  FOREIGN KEY (id_user) REFERENCES user (id_user) ON DELETE CASCADE,
  FOREIGN KEY (id_team) REFERENCES team (id_team) ON DELETE CASCADE	
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS championship;
CREATE TABLE championship (
  id_champ	integer	 NOT NULL AUTO_INCREMENT,
  name 		varchar(20) NOT NULL,
  data_limit	date NOT NULL,
  id_founded	integer NOT NULL,

  PRIMARY KEY (id_champ),
  UNIQUE KEY (name)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS inscription;
CREATE TABLE inscription (
  id_champ	integer	 NOT NULL,
  id_user	integer  NOT NULL,
  active 	integer  DEFAULT 0,

  PRIMARY KEY (id_champ, id_user),

  FOREIGN KEY (id_user)  REFERENCES user (id_user) ON DELETE CASCADE,
  FOREIGN KEY (id_champ)  REFERENCES championship (id_champ) ON DELETE CASCADE
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS circuit;
CREATE TABLE circuit (
  id_circuit	integer	 NOT NULL AUTO_INCREMENT,
  name		varchar(20)  NOT NULL,
  short_name 	varchar(20)  NOT NULL,
  code_description text ,
  level		integer ,
  n_laps	integer ,
  time		time	DEFAULT NULL,
  width		integer,
  height	integer,

  PRIMARY KEY (id_circuit),
  UNIQUE KEY (name, short_name)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS circuit_championship;
CREATE TABLE circuit_championship (
  id_champ	integer	 NOT NULL,
  id_circuit	integer  NOT NULL,

  PRIMARY KEY (id_champ, id_circuit),

  FOREIGN KEY (id_circuit)  REFERENCES circuit (id_circuit) ON DELETE CASCADE,
  FOREIGN KEY (id_champ)  REFERENCES championship (id_champ) ON DELETE CASCADE
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS game;
CREATE TABLE game (
  id_game	integer	 NOT NULL AUTO_INCREMENT,
  id_user	integer  NOT NULL,
  id_circuit 	integer  NOT NULL,
  id_champ	integer	 DEFAULT NULL,
  time_result	integer	 DEFAULT NULL,  
  time_insertion datetime DEFAULT NULL,

  PRIMARY KEY (id_game),

  FOREIGN KEY (id_user)  REFERENCES user (id_user) ON DELETE CASCADE,
  FOREIGN KEY (id_circuit, id_champ) REFERENCES circuit_championship (id_circuit, id_champ)

)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS cup;
CREATE TABLE cup (
  id_user	integer  NOT NULL,
  id_circuit 	integer  NOT NULL,

  PRIMARY KEY (id_user, id_circuit),

  FOREIGN KEY (id_user)  REFERENCES user (id_user),
  FOREIGN KEY (id_circuit) REFERENCES circuit (id_circuit)

)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

DROP TABLE IF EXISTS code;
CREATE TABLE code (
	file_name	varchar(20),
	file_date	date,
	code		text,
	id_user		integer,

	PRIMARY KEY (file_name, id_user),
	FOREIGN KEY (id_user)  REFERENCES user (id_user)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

INSERT INTO user (nick, name, surname1, surname2, email_user,
city, school, email_school, type_user, pass, activated, date_insertion) VALUES ('anonymous', 'anonymous', 'anonymous', null , 'anonymous@gmail.com', 'Blanes', 'Colegio1', 'cole@colegio1.com', 'alumno', '3434343434', 1, now());

INSERT INTO user (nick, name, surname1, surname2, email_user,
city, school, email_school, type_user, pass, date_insertion) VALUES ('dgb', 'David', 'Garcia', 'Bautista', 'dvdgarcia.83@gmail.com', 'Blanes', 'Colegio1', 'cole@colegio1.com', 'alumno', '123', '2008-1-1');

INSERT INTO user (nick, name, surname1, surname2, email_user,
city, school, email_school, type_user, pass, activated, date_insertion) VALUES ('asd', 'Mari', 'Juana', 'Rica', 'mjuana@gmail.com', 'Bcn', 'Colegio1', 'cole@colegio1.com', 'alumno', '123', 1, now());

INSERT INTO user (nick, name, surname1, surname2, email_user,
city, school, email_school, type_user, pass, activated, date_insertion) VALUES ('qwe', 'Omer', 'Jimenez', 'algo', 'omer.jimenez@gmail.com', 'Bcn', 'Colegio1', 'cole@colegio1.com', 'tutor', '123', 1, now());

INSERT INTO team (name, id_founded) VALUES ('Escuderia1', 3);

INSERT INTO team (name, id_founded) VALUES ('Escuderia2', 3);

INSERT INTO team (name, id_founded) VALUES ('Escuderia3', 3);

INSERT INTO user_team (id_user, id_team, active) VALUES (1,1,1);

INSERT INTO user_team (id_user, id_team, active) VALUES (2,1,1);

INSERT INTO user_team (id_user, id_team, active) VALUES (1,2,1);

INSERT INTO user_team (id_user, id_team, active) VALUES (2,2,0);

INSERT INTO user_team (id_user, id_team, active) VALUES (3,1,1);

INSERT INTO user_team (id_user, id_team, active) VALUES (3,2,1);

INSERT INTO circuit (name, short_name, level, n_laps, time, width, height) VALUES ('Montmelo', 'basic', 1, 3, null, 1000, 1000);

INSERT INTO circuit (name, short_name, level, n_laps, time, width, height) VALUES ('Jerez', 'basic2', 1, 3, null, 1000, 1000);

INSERT INTO circuit (name, short_name, level, n_laps, time, width, height) VALUES ('Turquia', 'chato', 1, 3, null, 1200, 500);

INSERT INTO circuit (name, short_name, level, n_laps, time, width, height) VALUES ('Circuito4', 'basic3', 1, 3, null, 1000, 1000);

INSERT INTO circuit (name, short_name, level, n_laps, time, width, height) VALUES ('Circuito5', 'newbasic', 1, 3, null, 1000, 1000);

INSERT INTO championship (name, data_limit, id_founded) VALUES ('Campeonato1', '2009-01-31', 3);

INSERT INTO championship (name, data_limit, id_founded) VALUES ('Campeonato2', '2011-01-31', 3);

INSERT INTO championship (name, data_limit, id_founded) VALUES ('Campeonato3', '2011-01-31', 3);

INSERT INTO inscription (id_user, id_champ, active) VALUES (1, 1, 1);

INSERT INTO inscription (id_user, id_champ, active) VALUES (2, 1, 1);

INSERT INTO inscription (id_user, id_champ, active) VALUES (2, 2, 1);

INSERT INTO inscription (id_user, id_champ, active) VALUES (2, 3, 0);

INSERT INTO inscription (id_user, id_champ, active) VALUES (3, 1, 0);

INSERT INTO inscription (id_user, id_champ, active) VALUES (3, 2, 0);

INSERT INTO inscription (id_user, id_champ, active) VALUES (3, 3, 1);

INSERT INTO circuit_championship (id_circuit, id_champ) VALUES (1, 1);

INSERT INTO circuit_championship (id_circuit, id_champ) VALUES (2, 2);

INSERT INTO circuit_championship (id_circuit, id_champ) VALUES (1, 3);

INSERT INTO circuit_championship (id_circuit, id_champ) VALUES (2, 1);

INSERT INTO circuit_championship (id_circuit, id_champ) VALUES (3, 1);

INSERT INTO circuit_championship (id_circuit, id_champ) VALUES (2, 3);

INSERT INTO circuit_championship (id_circuit, id_champ) VALUES (3, 3);

INSERT INTO circuit_championship (id_circuit, id_champ) VALUES (3, 2);

INSERT INTO game (id_user, id_circuit, id_champ, time_result, time_insertion) VALUES (2, 1, null, 75321, NOW());

INSERT INTO game (id_user, id_circuit, id_champ, time_result, time_insertion) VALUES (2, 1, 1, 75322, NOW());

INSERT INTO game (id_user, id_circuit, id_champ, time_result, time_insertion) VALUES (2, 1, 1, 75323, NOW());

/*INSERT INTO game (id_user, id_circuit, id_champ, time_result) VALUES (3, 1, 1, 75321);*/

INSERT INTO game (id_user, id_circuit, id_champ, time_result, time_insertion) VALUES (3, 1, 1, 75322, NOW());

INSERT INTO game (id_user, id_circuit, id_champ, time_result, time_insertion) VALUES (3, 1, 1, 75323, NOW());

INSERT INTO game (id_user, id_circuit, id_champ, time_result, time_insertion) VALUES (2, 2, null, 75324, NOW());

INSERT INTO game (id_user, id_circuit, id_champ, time_result, time_insertion) VALUES (2, 2, 2, 75325, NOW());

INSERT INTO game (id_user, id_circuit, id_champ, time_result, time_insertion) VALUES (2, 3, 1, 75326, NOW());

INSERT INTO game (id_user, id_circuit, id_champ, time_result, time_insertion) VALUES (3, 3, 2, 75327, NOW());

INSERT INTO game (id_user, id_circuit, id_champ, time_result) VALUES (3, 3, 2, 75328);

INSERT INTO game (id_user, id_circuit, id_champ, time_result) VALUES (3, 2, 3, 75329);
