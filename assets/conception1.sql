CREATE TABLE avion(
   id_avion SERIAL,
   libelle VARCHAR(50) ,
   date_fabrication DATE,
   PRIMARY KEY(id_avion)
);

CREATE TABLE ville(
   id_ville SERIAL,
   nom VARCHAR(50) ,
   PRIMARY KEY(id_ville)
);

CREATE TABLE vol(
   id_vol SERIAL,
   depart_vol TIMESTAMP,
   arrivee_vol TIMESTAMP,
   id_ville_depart INTEGER NOT NULL,
   id_ville_arrivee INTEGER NOT NULL,
   PRIMARY KEY(id_vol),
   FOREIGN KEY(id_ville_depart) REFERENCES ville(id_ville),
   FOREIGN KEY(id_ville_arrivee) REFERENCES ville(id_ville)
);

CREATE TABLE type_siege(
   id_type_siege SERIAL,
   libelle VARCHAR(50) ,
   PRIMARY KEY(id_type_siege)
);

CREATE TABLE configuration_limite(
   id_configuration SERIAL,
   libelle VARCHAR(50) ,
   nbre_heure INTEGER,
   PRIMARY KEY(id_configuration)
);

CREATE TABLE siege_avion_vol(
   id_siege_avion_vol SERIAL,
   nombre INTEGER NOT NULL,
   prix NUMERIC(15,2)   NOT NULL,
   id_type_siege INTEGER NOT NULL,
   id_vol INTEGER NOT NULL,
   id_avion INTEGER NOT NULL,
   PRIMARY KEY(id_siege_avion_vol),
   FOREIGN KEY(id_type_siege) REFERENCES type_siege(id_type_siege),
   FOREIGN KEY(id_vol) REFERENCES vol(id_vol),
   FOREIGN KEY(id_avion) REFERENCES avion(id_avion)
);

CREATE TABLE role(
   id_role SERIAL,
   libelle VARCHAR(50) ,
   PRIMARY KEY(id_role)
);

CREATE TABLE user_ticketing(
   id_user_client SERIAL,
   username VARCHAR(50) ,
   password VARCHAR(50) ,
   id_role INTEGER NOT NULL,
   PRIMARY KEY(id_user_client),
   FOREIGN KEY(id_role) REFERENCES role(id_role)
);

CREATE TABLE promotion(
   id_promotion SERIAL,
   valeur_pourcentage NUMERIC(15,2)  ,
   nombre_siege VARCHAR(50) ,
   id_avion_siege_vol INTEGER NOT NULL,
   PRIMARY KEY(id_promotion),
   UNIQUE(id_avion_siege_vol),
   FOREIGN KEY(id_avion_siege_vol) REFERENCES siege_avion_vol(id_siege_avion_vol)
);

CREATE TABLE reservation(
   id_user_client INTEGER,
   id_avion_siege_vol INTEGER,
   date_reservation TIMESTAMP,
   date_annulation TIMESTAMP,
   PRIMARY KEY(id_user_client, id_avion_siege_vol),
   FOREIGN KEY(id_user_client) REFERENCES user_ticketing(id_user_client),
   FOREIGN KEY(id_avion_siege_vol) REFERENCES siege_avion_vol(id_siege_avion_vol)
);
