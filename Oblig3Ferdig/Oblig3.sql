-- SQL for Oblig3

DROP SCHEMA IF EXISTS Oblig3 CASCADE;
CREATE SCHEMA Oblig3;
SET search_path TO Oblig3; 
    
CREATE TABLE Ansatt
(	
	AnsNr SERIAL,
	Brukernavn CHAR(4) UNIQUE NOT NULL,
	Fornavn VARCHAR(30) NOT NULL,
	Etternavn VARCHAR(30) NOT NULL, 
	Ansettelsesdato DATE NOT NULL,
	Maanedslonn DECIMAL(12, 2) NOT NULL,
	Avd_Id INTEGER NOT NULL,

	CONSTRAINT Ansatt_PK PRIMARY KEY (AnsNr)
	--, 
	--CONSTRAINT Avdeling_FK FOREIGN KEY (Sjef_Id) REFERENCES Avdeling(AvdelingNr)
);
	
CREATE TABLE Avdeling
(
	AvdelingNr SERIAL,
	AvdelingsNavn VARCHAR(30) NOT NULL,
	Sjef_Id INTEGER, -- NOT NULL
	CONSTRAINT Avdeling_PK PRIMARY KEY (avdelingNr),
	CONSTRAINT Sjef_FK FOREIGN KEY (Sjef_Id) REFERENCES Ansatt(AnsNr)
);

CREATE TABLE Prosjekt
(
	ProsjektNr SERIAL,
	ProsjektNavn VARCHAR(30) NOT NULL,
	Beskrivelse VARCHAR(200) NOT NULL,
	CONSTRAINT Prosjekt_PK PRIMARY KEY (ProsjektNr)
);

CREATE TABLE ProsjektDeltakelse
(	
	ProsjektDeltakelseNr SERIAL,
	AnsNr INTEGER,
 	ProsjektNr INTEGER,
	ArbeidsTimer INTEGER,
	Rolle VARCHAR(30) NOT NULL,
	CONSTRAINT ProsjektDeltakelse_PK PRIMARY KEY (ProsjektDeltakelseNr),
  	CONSTRAINT AnsattProsjekt_Unik UNIQUE (AnsNr, ProsjektNr),
  	CONSTRAINT Ansatt_FK FOREIGN KEY (AnsNr) REFERENCES Ansatt(AnsNr),
  	CONSTRAINT Prosjekt_FK FOREIGN KEY (ProsjektNr) REFERENCES Prosjekt(ProsjektNr)  
);

INSERT INTO
  	Avdeling(AvdelingsNavn)
  
VALUES
    ('Sekretær');

INSERT INTO
  Ansatt(Brukernavn, Fornavn, Etternavn, Ansettelsesdato, Maanedslonn, Avd_Id)

VALUES
    ('hib', 'Ole', 'Nød', '2021-05-30', 500500.50, 1);
	
UPDATE Avdeling SET Sjef_Id = 1 WHERE avdelingNr = 1;

ALTER TABLE Avdeling ALTER COLUMN sjef_ID SET NOT NULL;

INSERT INTO
  	Avdeling(AvdelingsNavn, Sjef_Id)
  
VALUES
    ('Programerer', 1),
	('Fotograf', 1);
	
INSERT INTO
  Ansatt(Brukernavn, Fornavn, Etternavn, Ansettelsesdato, Maanedslonn, Avd_Id)

VALUES
	('fla', 'Gurid', 'Sveio', '2020-03-30',  400500.50, 1),
	('fro', 'Svein', 'Sveio', '2019-03-30', 420500.50, 1),
	('flr', 'Berta', 'Sveio', '2018-03-30', 430500.50, 1),
	('flb', 'Tårn', 'Sveio', '2017-06-30',  440500.50, 2),
    	('aaa', 'Lilian', 'Sveio', '2020-02-27', 450500.50, 2),
    ('abc', 'Fedrik', 'Sveio', '2021-01-30', 460500.50, 2),
    ('abb', 'Sam', 'Sveio', '2021-03-20', 470500.50, 3),
    ('ccc', 'Berit', 'Sveio', '2021-03-10', 480500.50, 3),
    ('ddd', 'Tor kåre', 'Sveio', '2021-03-15', 490500.50, 3),
    ('eee', 'Fiona', 'Gram', '2020-05-12', 520900.50, 3);	
	
	UPDATE Avdeling SET Sjef_Id = 5 WHERE avdelingNr = 2;
	UPDATE Avdeling SET Sjef_Id = 9 WHERE avdelingNr = 3;

INSERT INTO
	Prosjekt (ProsjektNavn, Beskrivelse)

VALUES
	('Oblig3', 'Innleverings oppgove i Dat 107 JPA delen'),
	('Oblig2', 'Innleverings oppgove i Dat 107 SQL delen'),
	('Oblig1', 'Forste innleverings oppgove i Dat 107 SQL delen');

INSERT INTO
ProsjektDeltakelse (AnsNr, ProsjektNr, ArbeidsTimer, Rolle)

VALUES
	(1, 1, 10, 'Arbeider'),
	(2, 1, 20, 'Arbeider'),
	(3, 1, 30, 'Arbeider'),
	(4, 1, 11, 'Arbeider'),
	(5, 2, 15, 'Arbeider'),
	(6, 2, 30, 'Planlegger'),
	(7, 2, 3, 'Planlegger'),
	(8, 2, 1, 'HmsAnsvarleg'),
	(1, 3, 14, 'HmsAnsvarleg'),
	(2, 3, 39, 'HmsAnsvarleg'),
	(3, 3, 19, 'HmsAnsvarleg');