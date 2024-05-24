CREATE TABLE  Run(
    id INT NOT NULL,
    title varchar(250) NOT NULL,
    started_on timestamp NOT NULL,
    completed_on timestamp NOT NULL,
    distance INT NOT NULL,
    location varchar(10) NOT NULL,
    version INT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Tulemused
(
    koht       INT          NOT NULL,
    nr         INT          NOT NULL,
    nimi       varchar(250) NOT NULL,
    Sünniaasta INT          NOT NULL,
    Riik       varchar(10)  NOT NULL,
    Tulemus    INT          NOT NULL
)