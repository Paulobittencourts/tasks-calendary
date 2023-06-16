CREATE TABLE email(
    email_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    owner_ref varchar(120) NOT NULL,
    email_from varchar(120) NOT NULL,
    email_to varchar(120) NOT NULL,
    subject varchar(120) NOT NULL,
    text varchar(120) NOT NULL,
    send_date_email DATETIME NOT NULL,
    status_email BIGINT NOT NULL
);