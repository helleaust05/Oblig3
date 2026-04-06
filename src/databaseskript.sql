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

