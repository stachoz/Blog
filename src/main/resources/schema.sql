DROP TABLE if exists user_roles;
DROP TABLE if EXISTS user_role;
DROP TABLE if EXISTS user_coins;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS report;
DROP TABLE if EXISTS post;
DROP TABLE if EXISTS application_user;
DROP TABLE if EXISTS coin;

CREATE TABLE application_user
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL unique ,
    email VARCHAR(80) NOT NULL unique ,
    password VARCHAR(200) NOT NULL,
    UNIQUE (username, email)
);

CREATE TABLE coin
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) unique default NULL,
    current_price DECIMAL(20, 10) NOT NULL,
    date_time TIMESTAMP
);

CREATE TABLE post
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    content VARCHAR(2000) NOT NULL,
    time_added TIMESTAMP,
    author_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES application_user(id) on delete cascade
);

CREATE TABLE comment
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(500),
    time_added TIMESTAMP,
    author_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES application_user(id),
    FOREIGN KEY (post_id) REFERENCES post(id)
);


CREATE TABLE report
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cause VARCHAR(300),
    post_id BIGINT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES post(id)

);

CREATE TABLE user_coins
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    coin_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES application_user(id) on delete cascade,
    FOREIGN KEY (coin_id) REFERENCES coin(id) on delete cascade
);


CREATE TABLE user_role
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);

CREATE TABLE user_roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL ,
    role_id BIGINT NOT NULL ,
    FOREIGN KEY (user_id) REFERENCES application_user(id),
    FOREIGN KEY (role_id) REFERENCES user_role(id)
);