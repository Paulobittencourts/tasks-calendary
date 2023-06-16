CREATE TABLE roles(
    role_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role_name varchar(120) NOT NULL
);

INSERT INTO roles(role_name) VALUES('ROLE_USER');
INSERT INTO roles(role_name) VALUES('ROLE_ADMIN');