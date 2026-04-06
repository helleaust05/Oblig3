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