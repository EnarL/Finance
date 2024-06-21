CREATE TABLE  Run(
    id INT NOT NULL,
    title varchar(250) NOT NULL,
    started_On timestamp NOT NULL,
    completed_On timestamp NOT NULL,
    distance INT NOT NULL,
    location varchar(10) NOT NULL,
    version INT,
    PRIMARY KEY (id)
);

CREATE TABLE Client(
    id INT NOT NULL,
    username varchar(250) NOT NULL,
    password varchar(250) NOT NULL,
    email varchar(250) NOT NULL,
    version INT,
    PRIMARY KEY (id)
);
CREATE TABLE Expense(
    id INT NOT NULL,
    username varchar(250) NOT NULL,
    amount INT NOT NULL,
    category varchar(250) NOT NULL,
    description varchar(250) NOT NULL,
    date timestamp NOT NULL,
    version INT,
    PRIMARY KEY (id)
);

CREATE TABLE Income(
    id INT NOT NULL,
    username varchar(250) NOT NULL,
    amount INT NOT NULL,
    source varchar(250) NOT NULL,
    date timestamp NOT NULL,
    version INT,
    PRIMARY KEY (id)
);



