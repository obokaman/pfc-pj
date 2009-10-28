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
	pass		varchar(20) DEFAULT 12345,
	activated 	bool	    DEFAULT false,
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
  pendent 	integer  DEFAULT 0,

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
  pendent 	integer  DEFAULT 0,

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
  level		integer ,
  n_laps	integer ,
  time		time	DEFAULT NULL,

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
  time_result	time	 DEFAULT NULL,  

  PRIMARY KEY (id_game),

  FOREIGN KEY (id_user)  REFERENCES user (id_user),
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

INSERT INTO user (nick, name, surname1, surname2, email_user,
city, school, email_school, type_user, pass) VALUES ('dgb', 'David', 'Garcia', 'Bautista', 'dvdgarcia.83@gmail.com', 'Blanes', 'Colegio1', 'cole@colegio1.com', 'alumno', '123');

INSERT INTO user (nick, name, surname1, surname2, email_user,
city, school, email_school, type_user, pass, activated) VALUES ('asd', 'Mari', 'Juana', 'Rica', 'mjuana@gmail.com', 'Bcn', 'Colegio1', 'cole@colegio1.com', 'alumno', '123', 1);

INSERT INTO user (nick, name, surname1, surname2, email_user,
city, school, email_school, type_user, pass) VALUES ('qwe', 'Omer', 'Jimenez', 'algo', 'omer.jimenez@gmail.com', 'Bcn', 'Colegio1', 'cole@colegio1.com', 'tutor', '123');

INSERT INTO team (name, id_founded) VALUES ('Escuderia1', 3);

INSERT INTO user_team (id_user, id_team, pendent) VALUES (1,1,0);

INSERT INTO user_team (id_user, id_team, pendent) VALUES (2,1,0) ;

INSERT INTO circuit (name, short_name, level, n_laps, time) VALUES ('Montmelo', 'MNT', null, 3, null);

INSERT INTO circuit (name, short_name, level, n_laps, time) VALUES ('Jerez', 'JRZ', null, 3, null);

INSERT INTO circuit (name, short_name, level, n_laps, time) VALUES ('Turquia', 'TRQ', null, 3, null);

INSERT INTO championship (name, data_limit, id_founded) VALUES ('Campeonato1', '2009-01-31', 3);

INSERT INTO championship (name, data_limit, id_founded) VALUES ('Campeonato2', '2010-01-31', 3);

INSERT INTO inscription (id_user, id_champ) VALUES (1, 1);

INSERT INTO inscription (id_user, id_champ) VALUES (2, 1);

INSERT INTO inscription (id_user, id_champ) VALUES (2, 2);

INSERT INTO inscription (id_user, id_champ) VALUES (3, 1);

INSERT INTO inscription (id_user, id_champ) VALUES (3, 2);
