DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS company;
DROP TABLE IF EXISTS profile;

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

CREATE TABLE IF NOT EXISTS profile
(
    id       BIGSERIAL PRIMARY KEY,
    user_id  BIGINT NOT NULL UNIQUE REFERENCES users (id),
    street   VARCHAR(128),
    language CHAR(2)
);

CREATE TABLE IF NOT EXISTS chat
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users_chat
(
    user_id BIGINT REFERENCES users (id),
    chat_id BIGINT REFERENCES chat (id),
    PRIMARY KEY (user_id, chat_id)
);