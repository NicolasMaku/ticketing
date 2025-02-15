INSERT INTO ville (nom) VALUES
    ('Paris'),
    ('New York'),
    ('Tokyo'),
    ('Londres'),
    ('Berlin');

-- Insérer des avions (3 avions seulement)
INSERT INTO avion (libelle, date_fabrication) VALUES
      ('Boeing 737-800', '2017-05-20'),
      ('Airbus A320neo', '2020-08-15'),
      ('Embraer E195-E2', '2022-11-10');

-- Insérer des types de sièges
INSERT INTO type_siege (libelle) VALUES
     ('Économique'),
     ('Premium Économique'),
     ('Affaires'),
     ('Première Classe');


-- Boeing 737-800 (typique : 189 sièges)
INSERT INTO siege_avion (nombre, id_type_siege, id_avion) VALUES
      (150, 1, 1),  -- 150 sièges économiques
      (24, 2, 1),   -- 24 sièges premium économique
      (15, 3, 1);   -- 15 sièges affaires

-- Airbus A320neo (typique : 180 sièges)
INSERT INTO siege_avion (nombre, id_type_siege, id_avion) VALUES
      (162, 1, 2),  -- 162 sièges économiques
      (18, 3, 2);   -- 18 sièges affaires

-- Embraer E195-E2 (typique : 146 sièges)
INSERT INTO siege_avion (nombre, id_type_siege, id_avion) VALUES
      (118, 1, 3),  -- 118 sièges économiques
      (16, 2, 3),   -- 16 sièges premium économique
      (12, 3, 3);   -- 12 sièges affaires
