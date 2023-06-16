CREATE TABLE users(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(120) NOT NULL,
    password varchar(255) NOT NULL,
    email varchar(120) NOT NULL UNIQUE,
    username varchar(80) NOT NULL UNIQUE
);