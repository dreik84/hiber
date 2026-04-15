DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS company;

CREATE TABLE IF NOT EXISTS company
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(128) UNIQUE,
    firstname  VARCHAR(128),
    lastname   VARCHAR(128),
    birth_date DATE,
    role       VARCHAR(32),
    company_id INT REFERENCES company (id)
);

CREATE TABLE IF NOT EXISTS profile
(
    user_id  BIGINT PRIMARY KEY REFERENCES users (id),
    street   VARCHAR(128),
    language CHAR(2)
);