CREATE TABLE tasks
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name_task varchar(120) NOT NULL,
    status_task varchar(120) NOT NULL,
    date_task date NOT NULL,
    priority_task varchar(120) NOT NULL,
    user_id BIGINT NOT NULL
);