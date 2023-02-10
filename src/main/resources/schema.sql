DROP TABLE if EXISTS application_user;
DROP TABLE if EXISTS user_role;
DROP TABLE if EXISTS post;
DROP TABLE IF EXISTS comment;

CREATE TABLE application_user
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(80) NOT NULL ,
    password VARCHAR(200) NOT NULL
);
CREATE TABLE user_role
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL
);

CREATE TABLE user_roles
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NUll,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES application_user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES user_role(id) ON DELETE CASCADE
);

CREATE TABLE post
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    content VARCHAR(2000) NOT NULL,
    time_added TIMESTAMP,
    author_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES application_user(id)
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
