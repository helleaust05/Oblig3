CREATE TABLE Ansatt (
    ansattId Int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    brukernavn varchar(4) NOT NULL UNIQUE,
    fornavn varchar(30) NOT NULL,
    etternavn varchar(50) NOT NULL,
    ansattDato date,
    stilling varchar(50),
    manedslonn decimal(10, 2)
);

INSERT INTO ansatt (brukernavn, fornavn, etternavn, ansattDato, stilling, manedslonn)
VALUES
    ('lihe', 'Lilly', 'Henriksen', '2017-06-24', 'Nugattispiser', 333333),
    ('tola', 'Tonny','Larsen', '2011-09-05', 'professor', 98394);

CREATE TABLE Avdeling(
    avdelingId INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    avdelingNavn varchar(50) NOT NULL,
    sjefID INT
);

INSERT INTO Avdeling (avdelingNavn)
    VALUES ('X'), ('Y');
)

ALTER TABLE Avdeling ADD CONSTRAINT fkSjef FOREIGN KEY (sjefID) REFERENCES Ansatt(ansattId);

UPDATE Avdeling SET sjefId = 1 WHERE avdelingId = 1;
UPDATE Avdeling SET sjefId = 2 WHERE avdelingId = 2;

--liten feilretting her

ALTER TABLE ansatt ADD COLUMN avdelingid INT REFERENCES avdeling(avdelingid);

UPDATE ansatt SET avdelingid = 1 WHERE ansattid = 1;
UPDATE ansatt SET avdelingid = 1 WHERE ansattid = 2;

CREATE TABLE prosjekt (
                          prosjektid INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                          prosjektnavn varchar(50) NOT NULL,
                          beskrivelse varchar(300)
);

CREATE TABLE prosjektdeltakere (
                                   prosjektid INT REFERENCES prosjekt(prosjektid),
                                   ansattid INT REFERENCES ansatt(ansattid),
                                   PRIMARY KEY(ansattid, prosjektid)
);